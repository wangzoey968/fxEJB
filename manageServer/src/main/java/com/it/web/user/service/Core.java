package com.it.web.user.service;

import com.it.api.table.customer.Tb_Customer;
import com.it.api.table.customer.Tb_CustomerLog;
import com.it.api.table.user.*;
import com.it.util.HibernateUtil;
import com.it.util.ServerTask;
import com.it.util.base.CommonConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Core {

    //设置用户缓存,其中的key为sessionId
    public static Map<String, Tb_User> userMap = new ConcurrentHashMap<>();

    //设置客户缓存.其中key为sessionId
    public static Map<String, Tb_Customer> customerMap = new ConcurrentHashMap<>();

    //其中map中的key是uuid生成的string类的sessionId
    public static Map<String, Tb_UserLog> userLogMap = new ConcurrentHashMap<>();

    //其中map中的key是uuid生成的string类的sessionId
    public static Map<String, Tb_CustomerLog> customerLogMap = new ConcurrentHashMap<>();

    //用户ID，用户名 缓存
    private static Map<Long, String> usernameCache = new HashMap<>();

    private static Long timeOut = 1000 * 60 * 60 * 12L;

    public static void init() {

        Session session = HibernateUtil.openSession();

        Tb_Auth auth = (Tb_Auth) session.createQuery("from Tb_Auth where authname='超管'").uniqueResult();

        Tb_Role role = (Tb_Role) session.createQuery("from Tb_Role where rolename='超管'").uniqueResult();

        Tb_User user = (Tb_User) session.createQuery("from Tb_User where username='超管'").uniqueResult();

        if (auth == null) {
            auth = new Tb_Auth("超管", "", 0L);
            session.save(auth);
        }
        if (role == null) {
            role = new Tb_Role("超管", "", 0L);
            session.save(role);
        }
        if (user == null) {
            user = new Tb_User("18012345678", "超管", DigestUtils.md5Hex("123"), DigestUtils.md5Hex("123"), true, null);
            session.save(user);
        }

        /*Tb_User_Auth au = (Tb_User_Auth) session.createQuery("from Tb_User_Auth where tb_auth_id=:aid and tb_user_id=:uid")
                .setParameter("aid", auth.getId())
                .setParameter("uid", user.getId()).uniqueResult();
        if (au == null) {
            au = new Tb_User_Auth(auth.getId(), user.getId(), true);
            session.save(au);
        }*/

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

        //加载用户Session
        List<Tb_UserLog> list = session.createQuery("from Tb_UserLog where action like '%登录%'").list();

        for (Tb_UserLog s : list) {
            if (now - s.getActionTime() < timeOut) {
                userLogMap.put(s.getSessionId(), s);
            } /*else {
                session.delete(s);
            }*/
        }

        //创建Session过期检查任务；
        ServerTask.schedulePool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Long now = System.currentTimeMillis();
                for (Map.Entry<String, Tb_UserLog> entry : userLogMap.entrySet()) {
                    if (now - entry.getValue().getActionTime() > timeOut) {
                        try {
                            logout(entry.getKey());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (Map.Entry<String, Tb_CustomerLog> entry : customerLogMap.entrySet()) {
                    if (now - entry.getValue().getActionTime() > timeOut) {
                        try {
                            logout(entry.getKey());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("session检查,定时任务");
            }
        }, 60, 60, TimeUnit.SECONDS);

    }

    public static void destroy() {
        userMap.clear();
        customerMap.clear();
        userLogMap.clear();
        customerLogMap.clear();
    }

    /**
     * 登录
     * 返回登录的sessionId
     */
    public static String login(String loginname, String password) {
        Tb_UserLog log = null;
        try {
            Session session = HibernateUtil.openSession();
            Tb_User user = (Tb_User) session.createQuery("from Tb_User where loginname=:ln").setParameter("ln", loginname).uniqueResult();
            if (user == null) throw new Exception("无此用户");
            if (!user.getEnable()) throw new Exception("此用户已停用");
            if (!password.equals(user.getPassword())) throw new Exception("密码错误");
            user.setRoles(listUserRole(user.getId()));
            for (Tb_Role role : user.getRoles()) {
                role.setAuths(listRoleAuth(role.getId()));
            }
            user.setAuths(listUserAuth(user.getId()));

            log = new Tb_UserLog();
            log.setSessionId(UUID.randomUUID().toString());
            log.setAction(CommonConstant.LOGIN);
            log.setActionTime(System.currentTimeMillis());
            log.setTb_user_id(user.getId());
            session.save(log);
            //存入map中
            userMap.put(log.getSessionId(), user);
            userLogMap.put(log.getSessionId(), log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log.getSessionId();
    }

    /**
     * 用户注销；
     */
    public static void logout(String sessionId) {
        try {
            if (userLogMap.containsKey(sessionId)) {
                //从userMap中移除登录日志
                Tb_UserLog ul = userLogMap.get(sessionId);
                userLogMap.remove(sessionId);
                Session session = HibernateUtil.openSession();
                //session.refresh(ul);
                //保存退出日志
                Tb_UserLog log = new Tb_UserLog();
                log.setTb_user_id(ul.getTb_user_id());
                log.setSessionId(sessionId);
                log.setAction(CommonConstant.LOGOUT);
                log.setActionTime(System.currentTimeMillis());
                session.save(log);
            }
            userMap.remove(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从request的session中获取User对象,为网页使用
     */
    public static Tb_User getUser(HttpSession session) {
        Tb_User user = null;
        try {
            String sessionId = session.getAttribute("userSessionId").toString();
            if (!userMap.containsKey(sessionId)) {
                userMap.put(sessionId, getUser(sessionId));
            }
            user = userMap.get(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 设置的map中获取UserLog对象,再获取整个user对象,为客户端使用
     */
    public static Tb_User getUser(String sessionId) {
        Session session = HibernateUtil.openSession();
        Tb_UserLog ul = getUserLog(sessionId);
        Tb_User user = (Tb_User) session.createQuery("select user from Tb_User user where user.id=:uid").setParameter("uid", ul.getTb_user_id()).uniqueResult();
        //获取用户的所有角色
        user.setRoles(listUserRole(user.getId()));
        //获取角色下的所有权限
        for (Tb_Role role : user.getRoles()) {
            List<Tb_Auth> auths = listRoleAuth(role.getId());
            role.setAuths(auths);
        }
        //获取额外的权限
        user.setAuths(listUserAuth(user.getId()));
        return user;
    }

    /**
     * 获取UserLog对象；
     */
    public static Tb_UserLog getUserLog(String sessionId) {
        Tb_UserLog ul = null;
        try {
            if (userLogMap.containsKey(sessionId)) {
                Session session = HibernateUtil.openSession();
                ul = userLogMap.get(sessionId);
                ul.setActionTime(System.currentTimeMillis());
                session.update(ul);
            } else {
                throw new Exception("请先登录");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ul;
    }

    /**
     * 获取用户的所有角色
     */
    public static List<Tb_Role> listUserRole(Long userId) {
        Session session = HibernateUtil.openSession();
        List<Tb_Role> roles = session.createQuery("select role from Tb_User_Role ur left join Tb_Role role on role.id=ur.tb_role_id where ur.tb_user_id=:uid").setParameter("uid", userId).list();
        return roles;
    }

    /**
     * 获取单个角色下的权限
     */
    public static List<Tb_Auth> listRoleAuth(Long roleId) {
        Session session = HibernateUtil.openSession();
        List<Tb_Auth> auths = session.createQuery("select auth from Tb_Role_Auth ra left join Tb_Auth auth on ra.tb_auth_id=auth.id where ra.tb_role_id =:ids").setParameter("ids", roleId).list();
        return auths;
    }

    /**
     * 获取额外的权限
     */
    public static List<Tb_Auth> listUserAuth(Long userId) {
        Session session = HibernateUtil.openSession();
        List<Tb_Auth> auths = session.createQuery("select auth from Tb_User_Auth au left join Tb_Auth auth on au.tb_auth_id=auth.id where au.tb_user_id=:uid").setParameter("uid", userId).list();
        return auths;
    }

    /**
     * 获取用户下的全部权限
     */
    public static List<String> getUserAllAuths(Tb_User user) {
        ArrayList<String> list = new ArrayList<>();
        for (Tb_Role role : user.getRoles()) {
            for (Tb_Auth auth : role.getAuths()) {
                if (!list.contains(auth.getAuthname())) {
                    list.add(auth.getAuthname());
                }
            }
        }
        Session session = HibernateUtil.openSession();
        for (Tb_Auth auth : user.getAuths()) {
            Tb_User_Auth au = (Tb_User_Auth) session.createQuery("from Tb_User_Auth where tb_auth_id=:aid and tb_user_id=:uid").setParameter("aid", auth.getId()).setParameter("uid", user.getId()).uniqueResult();
            if (au != null) {
                if (list.contains(auth.getAuthname()) && !au.getExtend()) {
                    list.remove(auth.getAuthname());
                } else if (!list.contains(auth.getAuthname()) && au.getExtend()) {
                    list.add(auth.getAuthname());
                }
            }
        }
        System.out.println(list.toString() + "return list");
        return list;
    }

    /**
     * 根据用户ID获取用户名称；
     */
    public static String getUsername(Long userId) {
        if (userId == null) {
            return null;
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
