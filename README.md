# IntegratedFramework
IntegratedFramework Project
## 完成
* 生成数据库实体类，配置完实体关系。
* 实现大部分数据库接口，实现数据库实体和JSON的转换。
* 20170606:添加3D车间的基本信息访问接口，车间插入更新接口。
* 实现生成排程数据表的记录。
* 修复工艺实体中循环引用导致转换json失败的问题。
* 20170612:添加3D车间对设备、订单报表信息访问接口
* 实现排程时的数据库同步语句。
* 优化了数据库操作层的代码结构，精简代码。
* 20170613:添加对APS的socket的状态查询接口、增加APS对框架的计算结果反馈接口
* 修复了由于表结构的改动引起的对象序列化出现的无限循环问题
* 20170614:添加schedule和aps之间的统一标识;实现APS排程结束后，自动根据标识更新排程记录状态;增加对APS排程状态查询
* 20170615:添加设备故障处理，并通知前端
* 修复了之前多次排程发生的错误。
* 20170619:接收并解析APS结果同时产生快照记录。
* 20170619:增加设备调整故障处理前端访问接口
* 修复了不能都多次添加订单的问题
* 20170621:修改与3D车间的访问接口，增加对所有布局信息的请求。
* 20170622:完成与3D车间的调试，增加通过订单查找模拟数据。
* 20170623:添加用户配置信息，根据用户ID查找该用户的基本信息。取代global全局变量
* 20170624:增加服务器的跨域访问设置，通过添加拦截器并增加请求头
* 20170624:增加3D车间根据设备号单独更新布局状态
* 20170626:调整3D车间模拟数据回复格式;调整新建排程过程
           调整hibernate连接时区，解决8小时时差问题；解决中文显示问题
* 20170627:增加通过选取快照树节点，查看对应3D车间的模拟状态。
           增加选取快照树，将对应结果下发给MES(未嵌入activeMQ)
           增加主页面通知
           增加紧急插单APS调用处理接口
           增加根据订单ID删除订单及其相关联信息
* 20170628:增加设备和订单状态表；嵌入3D车间；部署3D车间至服务器
* 20170629:嵌入APS activeX控件，支持登录与切换
           增加主页流程图
* 20170630:调整页面所有资源的请求地址，使用相对地址代替;解决tomcat中直接部署问题
* 20170703:增加ActiveMQ库
* 20170705:增加服务器启动时自动启动hibernate和activemq服务
* 20170705:增加ActiveMQ的多线程解析、发送
* 20170706:增加sha1算法，用于给mes发送数据时添加数据校验标识
           增加对mes数据报文解析、存储
* 20170707:调整3d车间模拟数据处理接口
* 20170710:调整排程页面，增加自定义向导式排程
           增加3D车间和APS页面嵌入
           解决user_config无法创建问题，hibernate中不能存在default字段
* 20170711:解决3D车间和APS页面无法自动改变尺寸问题
           增加3D车间全屏、双屏显示问题
           调整引用库的结构
* 20170714:增加3d车间对所有订单的请求
* 20170715:增加布局清单请求;增加右侧快捷工具栏;增加等待页面;调整右侧快捷工具栏，加入图标;
* 20170716:增加右侧快捷工具的部分实现;移除无用的oldPage文件夹;修复快照因接口调整造成无法访问问题;
           调整快照，增加快照详细信息描述;增加排程跳转交互页面;
           增加涉及APS操所时，对aps状态的查询;
           增加对工序异常的处理;
* 20170717:修复排程错误问题;修复结果转换3d车间时，将快照对应的所有订单全部转换的问题;
           調整排程状态显示;新增异常处理时间;
           调整快照树详细信息显示，尺寸自适应。
           增加异常处理时类型和id的记录，便于结果返回时更新对应异常的状态信息
* 20170718:增加异常应急时，需要记录当前是哪种异常、哪个在应急，便于在接收返回结果时，更新对应异常的状态信息
           增加快照树时间节点，用于对快照按照时间排序;
           修复通知系统无法使用问题;
           调整排程模态页面点击空白位置消失的情况;修复第二次排程时页面不显示问题;
* 20170719:调整快照树节点按照时间显示;
           增加交互优化时自动使用按钮手动触发调用转换aps结果;
* 20170820:调整快照树；优化代码；
           根据APS的工艺重新制定3D车间转换规则;调整快照节点数据库，增加节点属性;
           增加布局的描述和图片;
           APS添加快照、恢复快照、发布订单、交互/应急滚动优化接口;
           修复前端通知每次都创建的问题;
* 20170721:与MES测试基本信息接口;增加资源中用于表示辅助资源的关键字assisantResource;
           完成aps快照、恢复快照等接口的回调更新操作;增加快照节点用于表示快照、恢复快照、下发订单等状态字段
* 20170722:修复排程页面日期显示不正确问题;统一页面通知类型;
           完成交互优化处理流程;
           增加紧急插单后，增加当前排程对应的记录信息。
           修复在IE下排程优化结束时间显示为nan的问题;
           调整排程页面中日历的事件的创建、根据时间重新筛选;
* 20170723:增加对布局的更新、插入布局产生故障记录;
           增加APS快捷工具栏;对process的转换进行了再次排查,解决几个字段转换错误的问题;
           移除订单页面中无需用户输入的字段;增加下发MES后调整订单状态问题;
* 20170724:暂时将3d请求改成opensesion;修复session访问同一个数据有缓存的问题;
           修复将日期字符串转换成日期对象问题;调整3D车间转换规则;
* 20170725:添加首页图;````
* 20170729:调整下发MES的对树形控件的动态更新;
* 20170730:调整日期显示为24小时制(前后端日期格式化时使用HH);支持快照的创建、恢复;nj
* 20170801:修复恢复快照不成功的问题;增加poemview显示当前调整的快照基础信息;
           增加下发mes时对aps订单的发布;调整3d车间订单显示订单名称;
           调整MES基础、实时信息接收存储过程;修复订单插入时，因日期不满足标准格式，而不能触发异常。
* 20170802:修复因应急失败，导致节点信息更新不及时问题;
* 20170803:完成与MES的接口调试
* 20180808:调整3D车间按照设备更新状态信息，使用设备名称取代id用于判断设备唯一性的标识;调整3D车间获取订单报表信息，将请求订单下所有的计划信息一并返回;
           增加3D车间请求模拟数据时对当前订单所包含设备的查询;
           将APS中关于kr16_mg的转换从转换规则中移除
* 20170809:工程集成了3D车间;实现了弹出层显示3d车间，打通activeMQ驱动3d车间的运动;
* 20170810:增加了日志记录，并按天记录在tomcat的logs文件夹目录下;
* 20170811:将订单和生产计划信息通过activeMq下发至MES;
* 20170816:首页事件只加载最近7天，并天单独显示;解决小车碰撞问题;
* 20170823:调整3D模拟信息，新增task信息;添加排程根据选择的布局来更新APS数据库


## 已知问题
* ~~数据存储依然有问题~~
* 3D车间访问订单和设备报表信息
* ~~DAOImpl里面的findallbyid应返回一个对象，而不是一个list.~~
* ~~transaction应该不用那么频繁的commit。~~
* 数据库中的表结构多对多的关系再确认一次，多对多的关系会导致序列化出错。
* 20170619个人告警中心表创建完成，但还未根据故障插入数据表
* ~~新建排程中的日期与系统时间相差8小时~~
* ~~框架plan表未转换成3D模拟数据格式~~
* 将排程结果下发给MES，~~未添加activeMQ查询~~
* IE11不支持3D车间，APS不支持chrome、firfox和edge
* 待编写MES的数据处理逻辑