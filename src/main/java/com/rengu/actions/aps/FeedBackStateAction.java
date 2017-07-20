package com.rengu.actions.aps;

import com.opensymphony.xwork2.ActionContext;
import com.rengu.DAO.aps.ApsDao;
import com.rengu.actions.SuperAction;
import com.rengu.entity.RG_OrderEntity;
import com.rengu.entity.RG_ScheduleEntity;
import com.rengu.entity.RG_SnapshotNodeEntity;
import com.rengu.entity.RG_UserConfigEntity;
import com.rengu.util.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * APS回调框架更新计算状态
 * Created by wey580231 on 2017/6/13.
 */
public class FeedBackStateAction extends SuperAction {

    private final String APS_RESULT_SUCCESS = "1";         //APS计算成功结果标识
    private final String APS_RESULT_FAIL = "0";            //APS计算失败结果标识

    private ApsDao apsDao = new ApsDao();

    //接收非aps结果回复，只用户信息等的提示
    public void recvApsState() {

    }

    //根据返回的id号更新对应schedule的状态
    public void recvApsResult() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameterMap = context.getParameters();

        boolean result = false;
        StringBuilder jsonString = new StringBuilder();

        if (parameterMap.size() == 3) {
            String[] id = (String[]) parameterMap.get("id");
            String[] state = (String[]) parameterMap.get("STATE");
            String[] message = (String[]) parameterMap.get("MESSAGE");

            System.out.println("=============收到回复消息啦============");


            if (id.length > 0 && state.length > 0 && message.length > 0) {
                switchResult(state[0]);
            }
        } else {
            WebSocketNotification.broadcast(Tools.creatNotificationMessage("APS计算出错!", "alert"));
        }
        Tools.jsonPrint(Tools.apsCode("ok", "1", "recive execute operation"), this.httpServletResponse);
    }

    //处理aps回调
    private void switchResult(String replyState) {

        RG_UserConfigEntity userconfig = UserConfigTools.getUserConfig("1");

        if (userconfig != null && userconfig.getRootSnapshotId().length() > 0) {
            MySessionFactory.getSessionFactory().getCurrentSession().close();
            Session session = MySessionFactory.getSessionFactory().getCurrentSession();
            if (!session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            Query query = session.createQuery("from RG_SnapshotNodeEntity entity where entity.id=:id");
            query.setParameter("id", userconfig.getRootSnapshotId());
            List list = query.list();
            if (list.size() > 0 && list.get(0) instanceof RG_SnapshotNodeEntity) {
                RG_SnapshotNodeEntity rootSnapshot = (RG_SnapshotNodeEntity) list.get(0);

                //创建快照节点
                query = session.createQuery("from RG_SnapshotNodeEntity entity where entity.id=:id");
                query.setParameter("id", userconfig.getMiddleSnapshotId());
                list = query.list();
                RG_SnapshotNodeEntity middleSnapshot = null;
                if (list.size() > 0) {
                    middleSnapshot = (RG_SnapshotNodeEntity) list.get(0);
                }

                //TODO 查询schedule时会级联查询出其对应的set集合
                RG_ScheduleEntity schedule = rootSnapshot.getSchedule();
                schedule.setEndCalcTime(new Date());

                String nodeName = "";

                int apsReplyCount = userconfig.getApsReplyCount().intValue();

                //不是应急排程
                if (!userconfig.isErrorSchedule()) {
                    if (replyState.equals(APS_RESULT_SUCCESS)) {
                        if (apsReplyCount == 0) {
                            schedule.setState(RG_ScheduleEntity.APS_SUCCESS);
                            nodeName = "基础计算结果";
                            WebSocketNotification.broadcast(Tools.creatNotificationMessage("APS计算完成!", "confirm"));
                            setOrdersState("0", schedule);
                        } else {
                            schedule.setState(RG_ScheduleEntity.APS_ADJUST);
                            if (middleSnapshot != null) {
                                nodeName = "优化调整" + middleSnapshot.getChilds().size();
                            }
                            WebSocketNotification.broadcast(Tools.creatNotificationMessage("APS优化计算完成!", "confirm"));
                            setOrdersState("0", schedule);
                        }
                    }
                    //计算失败
                    else if (!(replyState.equals(APS_RESULT_SUCCESS))) {
                        schedule.setState(RG_ScheduleEntity.APS_FAIL);
                        WebSocketNotification.broadcast(Tools.creatNotificationMessage("APS计算失败!", "alert"));
                        setOrdersState("0", schedule);
                    }
                }
                //故障应急排程
                else {
                    if (replyState.equals(APS_RESULT_SUCCESS)) {
                        if (apsReplyCount == 0) {
                            schedule.setState(RG_ScheduleEntity.ERROR_SUCCESS);
                            nodeName = "基础计算结果";
                            WebSocketNotification.broadcast(Tools.creatNotificationMessage("APS应急计算完成!", "confirm"));
                            setOrdersState("0", schedule);
                            setErrorState(userconfig.getErrorType(), userconfig.getErrorId(), ErrorState.ERROR_APS_FINISH);
                        } else {
                            schedule.setState(RG_ScheduleEntity.ERROR_ADJUST);
                            if (middleSnapshot != null) {
                                nodeName = "应急优化调整" + middleSnapshot.getChilds().size();
                            }
                            WebSocketNotification.broadcast(Tools.creatNotificationMessage("APS应急优化完成!", "confirm"));
                            setOrdersState("0", schedule);
                            setErrorState(userconfig.getErrorType(), userconfig.getErrorId(), ErrorState.ERROR_ADJUSTED);
                        }
                    }
                    //计算失败
                    else if (!(replyState.equals(APS_RESULT_SUCCESS))) {
                        schedule.setState(RG_ScheduleEntity.ERROR_FAIL);
                        WebSocketNotification.broadcast(Tools.creatNotificationMessage("APS应急处理失败!", "alert"));
                        setOrdersState("0", schedule);
                        setErrorState(userconfig.getErrorType(), userconfig.getErrorId(), ErrorState.ERROR_ERROR);
                    }
                }

                UserConfigTools.updateApsReplyCount("1", apsReplyCount + 1);

                RG_SnapshotNodeEntity bottomSnapshot = null;

                if (replyState.equals(APS_RESULT_SUCCESS) && middleSnapshot != null) {
                    bottomSnapshot = new RG_SnapshotNodeEntity();
                    bottomSnapshot.setId(Tools.getUUID());
                    bottomSnapshot.setName(nodeName);
                    bottomSnapshot.setLevel(SnapshotLevel.BOTTOM);
                    bottomSnapshot.setNodeCreateTime(new Date());
                    bottomSnapshot.setApply(false);
                    if (userconfig.isErrorSchedule()) {
                        bottomSnapshot.setErrorNode(true);
                    } else {
                        bottomSnapshot.setErrorNode(false);
                    }

                    if (apsReplyCount == 0) {
                        bottomSnapshot.setFirstNode(true);
                    } else {
                        bottomSnapshot.setFirstNode(false);
                    }

                    bottomSnapshot.setParent(middleSnapshot);
                    bottomSnapshot.setRootParent(rootSnapshot);

                    UserConfigTools.updateBottomSnapshotId("1", bottomSnapshot.getId());

                    middleSnapshot.getChilds().add(bottomSnapshot);

                    session.save(middleSnapshot);
                }

                //APS_PLAN-->RG_PlanEntity共用一个session,确保在一个实务之中
                if (replyState.equals(APS_RESULT_SUCCESS)) {
                    try {
                        ApsTools.instance().getScheduleResult(bottomSnapshot);

                        session.getTransaction().commit();

                    } catch (Exception e) {
                        e.printStackTrace();
                        session.getTransaction().rollback();
                        WebSocketNotification.broadcast(Tools.creatNotificationMessage("APS计算结果转换出错!", "alert"));
                        setOrdersState("0", schedule);
                    }
                } else {
                    session.update(schedule);
                    session.getTransaction().commit();
                }
            } else {
                WebSocketNotification.broadcast(Tools.creatNotificationMessage("无法获取快照节点!", "alert"));
                session.getTransaction().commit();
            }
        }
    }

    //查询APS状态,在对APS数据库操作之前，都要先执行状态查询操作
    public void queryApsState() {
        StringBuilder result = new StringBuilder();

        if (apsDao.queryCurrState(result)) {
            Tools.jsonPrint(result.toString(), this.httpServletResponse);
        } else {
            Tools.jsonPrint(Tools.resultCode("error", "Can't execute operation"), this.httpServletResponse);
        }
    }

    //获取最新排程的基础信息
    public void queryScheduleInfo() {
        StringBuilder result = new StringBuilder();
        if (apsDao.getScheduleInfo(result)) {
            Tools.jsonPrint(result.toString(), this.httpServletResponse);
        } else {
            Tools.jsonPrint(Tools.resultCode("error", "Can't execute operation"), this.httpServletResponse);
        }
    }

    //设置订单状态
    private void setOrdersState(String state, RG_ScheduleEntity rg_scheduleEntity) {
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        Set<RG_OrderEntity> rg_orderEntitySet = rg_scheduleEntity.getOrders();
        if (rg_orderEntitySet.size() >= 0) {
            for (RG_OrderEntity orderEntity : rg_orderEntitySet) {
                orderEntity.setState(Byte.parseByte(state));
                session.save(orderEntity);
            }
        }
    }

    //设置对应故障的状态
    private int setErrorState(String errorType, String errorId, Integer state) {
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        NativeQuery tquery = session.createNativeQuery("update " + errorType + " set state = ? where id = ?");
        tquery.setParameter(1, state);
        tquery.setParameter(2, errorId);

        return tquery.executeUpdate();
    }

    //模拟aps应急优化结果
    public void emulateApsInterResult() {
//        switchResult("1");
        System.out.println("hah");
        Tools.jsonPrint(Tools.resultCode("ok", "start switch result!"), this.httpServletResponse);
    }
}
