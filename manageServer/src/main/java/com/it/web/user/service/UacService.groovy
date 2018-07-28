package com.it.web.user.service

import com.it.api.table.Tb_Role
import com.it.api.table.Tb_User
import com.it.api.table.Tb_UserSession
import com.it.api.table.customer.Tb_CustomerSession
import com.it.util.HibernateUtil
import com.it.web.user.dao.UacDao
import org.apache.commons.codec.digest.DigestUtils
import org.hibernate.Session

import javax.servlet.http.HttpSession
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class UacService {

    public static Map<String, Tb_UserSession> sUserMap = new ConcurrentHashMap<>();

    public static Map<String, Tb_CustomerSession> sCusMap = new ConcurrentHashMap<>();

    private static Long timeOut = 1000 * 60 * 60 * 12;

    public static Tb_User getSysUser() {
        new Tb_User(userId: 0, loginName: '00000000000', userName: '系统', role: '系统', enable: true, authList: UacAuthList.auths)
    }

    static void init() {
        HibernateUtil.openSession { Session session ->
            Long now = System.currentTimeMillis();
            UacDao uacDao = new UacDao(session: session);
            //加载用户Session
            uacDao.selectUserSession().each {
                if (now - it.lastAccessTime < timeOut) {
                    sUserMap.put(it.sessionId, it)
                } else {
                    session.delete(it)
                }
            }
            //加载客户Session
            session.createQuery('FROM Tb_CustomerSession').list().each { Tb_CustomerSession cs ->
                if (now - cs.lastAccessTime < timeOut) {
                    sCusMap.put(cs.sessionId, cs)
                } else {
                    session.delete(cs)
                }
            }
            //检查是否有管理员角色；
            Tb_Role role = session.createQuery("FROM Tb_Role WHERE roleName='管理员'").uniqueResult() as Tb_Role;
            if (role == null) {
                role = new Tb_Role(roleName: '管理员')
                session.save(role);
                session.saveOrUpdate(new Tb_RoleAuth(roleName: '管理员', authName: '用户管理'))
            }
            //检查是否有管理员用户；
            if (session.createQuery("FROM Tb_User WHERE role='管理员'").list().size() == 0) {
                session.save(new Tb_User(
                        loginName: '18012345678',
                        userName: '管理员',
                        password: DigestUtils.md5Hex('123'),
                        role: '管理员',
                        enable: true))
            }
        }
        //创建Session过期检查任务；
        ServerTasks.ses.scheduleWithFixedDelay({
            Long now = System.currentTimeMillis();
            sUserMap.each { key, value ->
                if (now - value.lastAccessTime > timeOut) {
                    logout(key)
                }
            }
            sCusMap.each { key, value ->
                if (now - value.lastAccessTime > timeOut) {
                    cusLogout(key)
                }
            }
        }, 60, 60, TimeUnit.SECONDS)
    }


    static void destroy() {
        HibernateUtil.openSession { Session session ->
            sUserMap.each { session.saveOrUpdate(it.value) }
            sCusMap.each { session.saveOrUpdate(it.value) }
        }
    }

    /**
     * 客户登录;
     */
    public static String cusLogin(Long customerId, String password) {
        HibernateUtil.openSession { Session session ->
            Tb_Customer customer = session.createQuery('FROM Tb_Customer WHERE customerId=:customerId')
                    .setParameter('customerId', customerId).uniqueResult() as Tb_Customer;
            if (customer == null) throw new Exception('Id错误');
            if (customer.password != password) throw new Exception('密码错误');
            Tb_CustomerSession customerSession = new Tb_CustomerSession(
                    sessionId: UUID.randomUUID().toString(),
                    customerId: customer.customerId,
                    lastAccessTime: System.currentTimeMillis())
            session.saveOrUpdate(customerSession);
            sCusMap.put(customerSession.sessionId, customerSession);
            return customerSession.sessionId
        }
    }

    /**
     * 客户注销
     */
    public static void cusLogout(String sid) {
        try {
            if (sCusMap.containsKey(sid)) {
                def us = sCusMap.get(sid);
                sCusMap.remove(us);
                HibernateUtil.openSession {
                    Session session ->
                        session.refresh(us)
                        session.delete(us)
                }
            }
        } catch (e) {
        }
    }

    /**
     * 用户登录
     * @param loginName 手机号
     * @param password 密码
     * @param ddLogin 是否钉钉登录，如果是钉钉登录，无需验证密码；
     * @return
     * @throws Exception
     */
    static String login(String loginName, String password, Boolean ddLogin = false) throws Exception {
        HibernateUtil.openSession {
            UacDao dao = new UacDao(session: it);
            Tb_User user = dao.selectUserByLoginName(loginName);
            if (user == null) throw new Exception('无此用户');
            if (!user.enable) throw new Exception('此用户已停用');
            if (!ddLogin) {
                if (user.password != password) throw new Exception('密码错误');
            }
            Tb_UserSession userSession = new Tb_UserSession(
                    sessionId: UUID.randomUUID().toString(),
                    userId: user.userId,
                    lastAccessTime: System.currentTimeMillis())
            dao.saveOrUpdate(userSession)
            sUserMap.put(userSession.sessionId, userSession);
            return userSession.sessionId;
        }
    }
    /**
     * 用户注销；
     */
    static void logout(String sid) throws Exception {
        try {
            if (sUserMap.containsKey(sid)) {
                def us = sUserMap.get(sid);
                sUserMap.remove(us);
                HibernateUtil.openSession {
                    Session session ->
                        session.refresh(us)
                        session.delete(us)
                }
            }
        } catch (e) {
        }
    }
    /**
     * 获取UserSession对象；
     */
    static Tb_UserSession getUserSession(String sid) {
        if (sUserMap.containsKey(sid)) {
            def us = sUserMap.get(sid);
            us.lastAccessTime = System.currentTimeMillis();
            return us;
        } else {
            throw new Exception("未登录或登录超时，请重新登录。");
        }
    }
    /**
     *  获取User对象；
     */
    static Tb_User getUser(String sid) throws Exception {
        Tb_UserSession us = getUserSession(sid);
        (Tb_User) HibernateUtil.openSession {
            UacDao uacDao = new UacDao(session: it);
            Tb_User user = uacDao.selectUserByUserId(us.userId)
            user.authList = uacDao.listUserAuth(user);
            return user;
        }
    }

    /**
     * 获取UserSession对象；
     */
    static Tb_UserSession getUserSession(HttpSession session) {
        getUserSession(session.getAttribute('userSid').toString())
    }
    /**
     *  获取User对象；
     */
    static Tb_User getUser(HttpSession session) throws Exception {
        getUser(session.getAttribute('userSid').toString())
    }
    /**
     * 获取CustomerSession
     */
    static Tb_CustomerSession getCusSession(HttpSession session) {
        getCusSession(session.getAttribute('cusSid').toString())
    }
    /**
     * 获取CustomerSession
     */
    static Tb_CustomerSession getCusSession(String cusSid) {
        if (sCusMap.containsKey(cusSid)) {
            def cus = sCusMap.get(cusSid);
            cus.lastAccessTime = System.currentTimeMillis();
            return cus;
        } else {
            throw new Exception("未登录或登录超时，请重新登录。");
        }
    }

    /**
     * 用户ID，用户名称 缓存
     */
    private static Map<Long, String> userNameCache = new HashMap<>();
    /**
     * 根据用户ID获取用户名称；
     */
    static String getUserName(Long userId) {
        if (userId == null) {
            return null
        } else if (userId == 0) {
            return '系统'
        } else if (userNameCache.containsKey(userId)) {
            return userNameCache.get(userId)
        } else {
            return HibernateUtil.openSession {
                Session session ->
                    Tb_User user = session.createQuery('FROM Tb_User WHERE userId=:id')
                            .setParameter('id', userId).uniqueResult() as Tb_User;
                    if (user == null) return null
                    userNameCache.put(userId, user.userName);
                    return user.userName;
            }
        }
    }

}
