package com.it.web.user.service;

import com.it.api.table.user.Tb_Auth;
import com.it.api.table.user.Tb_Role;
import com.it.api.table.user.Tb_User;
import com.it.api.table.user.Tb_UserLog;
import com.it.api.table.customer.Tb_Customer;
import com.it.api.table.customer.Tb_CustomerSession;
import com.it.util.HibernateUtil;
import com.it.util.ServerTask;
import com.it.web.user.dao.AuthDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class AuthService {

    public static Map<String, Tb_UserLog> userMap = new ConcurrentHashMap<>();

    public static Map<String, Tb_CustomerSession> customerMap = new ConcurrentHashMap<>();

    private static Long timeOut = 1000 * 60 * 60 * 12L;

    public static Tb_User getSysUser() {
        return new Tb_User("00000000000", "系统", "123", "123456", "管理员", true, AuthList.auths);
    }

    public static void init() {
        Session session = HibernateUtil.openSession();
        Long now = System.currentTimeMillis();
        AuthDao authDao = new AuthDao();
        //加载用户Session
        List<Tb_UserLog> list = authDao.selectUserSession();
        for (Tb_UserLog s : list) {
            if (now - s.getLastAccessTime() < timeOut) {
                userMap.put(s.getSessionId(), s);
            } else {
                session.delete(s);
            }
        }

        //加载客户Session
        List<Tb_CustomerSession> cusSession = session.createQuery("FROM Tb_CustomerSession").list();
        for (Tb_CustomerSession c : cusSession) {
            if (now - c.getLastAccessTime() < timeOut) {
                customerMap.put(c.getSessionId(), c);
            } else {
                session.delete(c);
            }
        }

        //检查是否有管理员角色；
        Tb_Role role = (Tb_Role) session.createQuery("FROM Tb_Role WHERE rolename=\"管理员\"").uniqueResult();
        if (role == null) {
            role = new Tb_Role();
            role.setRolename("管理员");
            session.save(role);
            Tb_Auth auth = new Tb_Auth();
            auth.setAuthname("管理员");
            auth.setNote("备注");
            session.saveOrUpdate(auth);
        }
        //检查是否有管理员用户；
        if (session.createQuery("FROM Tb_User WHERE role=\"管理员\"").list().size() == 0) {
            Tb_User user = new Tb_User();
            user.setLoginname("18012345678");
            user.setUsername("管理员");
            user.setPassword(DigestUtils.md5Hex("123"));
            user.setRole("管理员");
            user.setEnable(true);
            session.save(user);
        }

        //创建Session过期检查任务；
        ServerTask.schedulePool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Long now = System.currentTimeMillis();
                for (Map.Entry<String, Tb_UserLog> entry : userMap.entrySet()) {
                    if (now - entry.getValue().getLastAccessTime() > timeOut) {
                        try {
                            logout(entry.getKey());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (Map.Entry<String, Tb_CustomerSession> entry : customerMap.entrySet()) {
                    if (now - entry.getValue().getLastAccessTime() > timeOut) {
                        try {
                            logout(entry.getKey());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    public static void destroy() {
        Session session = HibernateUtil.openSession();
        for (Map.Entry<String, Tb_UserLog> entry : userMap.entrySet()) {
            session.saveOrUpdate(entry.getValue());
        }
        for (Map.Entry<String, Tb_CustomerSession> entry : customerMap.entrySet()) {
            session.saveOrUpdate(entry.getValue());
        }
    }

    /**
     * 客户登录;
     */
    public static String customerLogin(Long customerId, String password) throws Exception {
        Session session = HibernateUtil.openSession();
        Tb_Customer cus = (Tb_Customer) session.createQuery("from Tb_Customer where id=:id").setParameter("id", customerId).uniqueResult();
        if (cus == null) throw new Exception("id错误");
        if (!cus.getPassword().equals(password)) throw new Exception("密码错误");

        Tb_CustomerSession cusSession = new Tb_CustomerSession();
        cusSession.setCustomerId(cus.getId());
        cusSession.setSessionId(UUID.randomUUID().toString());
        cusSession.setLastAccessTime(System.currentTimeMillis());
        session.saveOrUpdate(cusSession);
        customerMap.put(cusSession.getSessionId(), cusSession);
        return cusSession.getSessionId();
    }

    /**
     * 客户注销
     */
    public static void customerLogout(String sid) {
        try {
            if (customerMap.containsKey(sid)) {
                Tb_CustomerSession us = customerMap.get(sid);
                customerMap.remove(us);
                Session session = HibernateUtil.openSession();
                session.refresh(us);
                session.delete(us);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录
     *
     * @param loginName 手机号
     * @param password  密码
     * @param ddLogin   是否钉钉登录，如果是钉钉登录，无需验证密码；
     */
    public static String login(String loginName, String password, Boolean ddLogin) throws Exception {
        AuthDao dao = new AuthDao();
        Tb_User user = dao.selectUserByLoginName(loginName);
        if (user == null) throw new Exception("无此用户");
        if (!user.getEnable()) throw new Exception("此用户已停用");
        if (!ddLogin) {
            if (!user.getPassword().equals(password)) throw new Exception("密码错误");
        }
        Tb_UserLog userSession = new Tb_UserLog();
        userSession.setSessionId(UUID.randomUUID().toString());
        userSession.setUserId(user.getId());
        userSession.setLastAccessTime(System.currentTimeMillis());
        dao.saveOrUpdate(userSession);
        userMap.put(userSession.getSessionId(), userSession);
        return userSession.getSessionId();
    }

    /**
     * 用户注销；
     */
    public static void logout(String sid) throws Exception {
        try {
            if (userMap.containsKey(sid)) {
                Tb_UserLog us = userMap.get(sid);
                userMap.remove(us);
                Session session = HibernateUtil.openSession();
                session.refresh(us);
                session.delete(us);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取UserSession对象；
     */
    public static Tb_UserLog getUserSession(String sid) {
        Tb_UserLog us = null;
        if (userMap.containsKey(sid)) {
            us = userMap.get(sid);
            us.setLastAccessTime(System.currentTimeMillis());
            Session session = HibernateUtil.openSession();
            session.update(session);
        }
        return us;
    }

    /**
     * 获取User对象；
     */
    public static Tb_User getUser(String sid) throws Exception {
        Tb_UserLog us = getUserSession(sid);
        AuthDao uacDao = new AuthDao();
        Tb_User user = uacDao.selectUserByUserId(us.getUserId());
        //user.setAuthList(uacDao.listUserAuth(user));
        return user;
    }

    /**
     * 获取UserSession对象；
     */
    public static Tb_UserLog getUserSession(HttpSession session) {
        return getUserSession(session.getAttribute("userSid").toString());
    }

    /**
     * 获取User对象；
     */
    public static Tb_User getUser(HttpSession session) throws Exception {
        return getUser(session.getAttribute("userSid").toString());
    }

    /**
     * 获取CustomerSession
     */
    public static Tb_CustomerSession getCusSession(HttpSession session) {
        return getCusSession(session.getAttribute("cusSid").toString());
    }

    /**
     * 获取CustomerSession
     */
    public static Tb_CustomerSession getCusSession(String cusSid) {
        Tb_CustomerSession cus = null;
        try {
            if (customerMap.containsKey(cusSid)) {
                cus = customerMap.get(cusSid);
                cus.setLastAccessTime(System.currentTimeMillis());
                HibernateUtil.openSession().update(cus);
            } else {
                throw new Exception("未登录或登录超时，请重新登录。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cus;
    }

    /**
     * 用户ID，用户名称 缓存
     */
    private static Map<Long, String> userNameCache = new HashMap<>();

    /**
     * 根据用户ID获取用户名称；
     */
    public static String getUserName(Long userId) {
        if (userId == null) {
            return null;
        } else if (userId == 0) {
            return "系统";
        } else if (userNameCache.containsKey(userId)) {
            return userNameCache.get(userId);
        } else {
            Session session = HibernateUtil.openSession();
            Tb_User user = (Tb_User) session.createQuery("FROM Tb_User WHERE id=:id")
                    .setParameter("id", userId).uniqueResult();
            if (user == null) return null;
            userNameCache.put(userId, user.getUsername());
            return user.getUsername();
        }
    }

}
