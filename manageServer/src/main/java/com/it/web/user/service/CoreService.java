package com.it.web.user.service;

import com.it.api.table.customer.Tb_CustomerLog;
import com.it.api.table.user.*;
import com.it.util.HibernateUtil;
import com.it.util.ServerTask;
import com.it.util.base.CommonConstant;
import com.it.web.user.dao.CoreDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CoreService {

    public static Map<Long, Tb_UserLog> userMap = new ConcurrentHashMap<>();

    public static Map<Long, Tb_CustomerLog> customerMap = new ConcurrentHashMap<>();

    private static Long timeOut = 1000 * 60 * 60 * 12L;

    public static void init() {

        Session session = HibernateUtil.openSession();

        Tb_Auth auth = (Tb_Auth) session.createQuery("from Tb_Auth where authname='超管'").uniqueResult();

        Tb_Role role = (Tb_Role) session.createQuery("from Tb_Role where rolename='超管'").uniqueResult();

        Tb_User user = (Tb_User) session.createQuery("from Tb_User where username='超管'").uniqueResult();

        if (auth == null) {
            auth = new Tb_Auth("超管", "", 0L, null, null, null);
            session.save(auth);
        }
        if (role == null) {
            role = new Tb_Role("超管", "", 0L, null, null, null);
            session.save(role);
        }
        if (user == null) {
            user = new Tb_User("18012345678", "超管", DigestUtils.md5Hex("123"), DigestUtils.md5Hex("123"), true, null, null, null, null);
            session.save(user);
        }

        Tb_Auth_User au = (Tb_Auth_User) session.createQuery("from Tb_Auth_User where tb_auth_id=:aid and tb_user_id=:uid")
                .setParameter("aid", auth.getId())
                .setParameter("uid", user.getId()).uniqueResult();
        if (au == null) {
            au = new Tb_Auth_User(auth.getId(), user.getId());
            session.save(au);
        }

        Tb_Role_Auth ra = (Tb_Role_Auth) session.createQuery("from Tb_Role_Auth where tb_role_id=:rid and tb_auth_id=:aid")
                .setParameter("rid", role.getId())
                .setParameter("aid", auth.getId()).uniqueResult();
        if (ra == null) {
            ra = new Tb_Role_Auth(role.getId(), auth.getId());
            session.save(ra);
        }

        Tb_User_Role ur = (Tb_User_Role) session.createQuery("from Tb_User_Role where tb_user_id=:uid and tb_role_id=:rid")
                .setParameter("uid", user.getId())
                .setParameter("rid", role.getId()).uniqueResult();
        if (ur == null) {
            ur = new Tb_User_Role(role.getId(), user.getId());
            session.save(ur);
        }

        Long now = System.currentTimeMillis();
        CoreDao authDao = new CoreDao();

        //加载用户Session
        List<Tb_UserLog> list = authDao.selectUserLog();
        for (Tb_UserLog s : list) {
            if (now - s.getActionTime() < timeOut) {
                userMap.put(s.getId(), s);
            } else {
                session.delete(s);
            }
        }

        //创建Session过期检查任务；
        ServerTask.schedulePool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Long now = System.currentTimeMillis();
                for (Map.Entry<Long, Tb_UserLog> entry : userMap.entrySet()) {
                    if (now - entry.getValue().getActionTime() > timeOut) {
                        try {
                            logout(entry.getKey());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (Map.Entry<Long, Tb_CustomerLog> entry : customerMap.entrySet()) {
                    if (now - entry.getValue().getActionTime() > timeOut) {
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
        for (Map.Entry<Long, Tb_UserLog> entry : userMap.entrySet()) {
            session.saveOrUpdate(entry.getValue());
        }
    }

    /**
     * 登录
     *
     * @param loginname 手机号
     * @param password  密码
     * @param ddLogin   是否钉钉登录，如果是钉钉登录，无需验证密码；
     */
    public static Long login(String loginname, String password, Boolean ddLogin) throws Exception {
        CoreDao dao = new CoreDao();
        Tb_User user = dao.selectUserByLoginName(loginname);
        if (user == null) throw new Exception("无此用户");
        if (!user.getEnable()) throw new Exception("此用户已停用");
        if (!ddLogin) {
            if (!user.getPassword().equals(password)) throw new Exception("密码错误");
        }
        Tb_UserLog log = new Tb_UserLog();
        log.setAction(CommonConstant.LOGIN);
        log.setActionTime(System.currentTimeMillis());
        log.setTb_user_id(user.getId());
        dao.saveOrUpdate(log);
        return log.getId();
    }

    /**
     * 用户注销；
     */
    public static void logout(Long sid) throws Exception {
        try {
            if (userMap.containsKey(sid)) {
                Tb_UserLog us = userMap.get(sid);
                userMap.remove(sid);
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
            us.setActionTime(System.currentTimeMillis());
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
        CoreDao authDao = new CoreDao();
        Tb_User user = authDao.selectUserByUserId(us.getTb_user_id());
        //user.setAuthList(authDao.listUserAuth(user));
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
     * 用户ID，用户名 缓存
     */
    private static Map<Long, String> usernameCache = new HashMap<>();

    /**
     * 根据用户ID获取用户名称；
     */
    public static String getUserName(Long userId) {
        if (userId == null) {
            return null;
        } else if (userId == 0) {
            return "系统";
        } else if (usernameCache.containsKey(userId)) {
            return usernameCache.get(userId);
        } else {
            Session session = HibernateUtil.openSession();
            Tb_User user = (Tb_User) session.createQuery("FROM Tb_User WHERE id=:id")
                    .setParameter("id", userId).uniqueResult();
            if (user == null) return null;
            usernameCache.put(userId, user.getUsername());
            return user.getUsername();
        }
    }

}
