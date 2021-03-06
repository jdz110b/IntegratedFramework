package com.rengu.DAO.impl;

import com.rengu.DAO.AssisantprocessDAO;
import com.rengu.entity.RG_AssisantprocessEntity;
import com.rengu.util.MySessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by hanchangming on 2017/5/31.
 */
public class AssisantprocessDAOImpl extends SuperDAOImpl implements AssisantprocessDAO<RG_AssisantprocessEntity> {
    @Override
    public List<RG_AssisantprocessEntity> findAll() {
        Session st = MySessionFactory.getSessionFactory().getCurrentSession();
        st.close();
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();

        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            session.beginTransaction();
        }
        String hql = "from RG_AssisantprocessEntity rg_assisantprocessEntity";
        Query query = session.createQuery(hql);
        List list = query.list();
        return list;
    }

    @Override
    public RG_AssisantprocessEntity findAllById(String id) {
        try {
            MySessionFactory.getSessionFactory().getCurrentSession().close();
            Session session = MySessionFactory.getSessionFactory().getCurrentSession();
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            String hql = "from RG_AssisantprocessEntity rg_assisantprocessEntity where rg_assisantprocessEntity.id =:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            if (!query.list().isEmpty()) {
                RG_AssisantprocessEntity rg_assisantprocessEntity = (RG_AssisantprocessEntity) query.list().get(0);
                return rg_assisantprocessEntity;
            } else {
                return null;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
