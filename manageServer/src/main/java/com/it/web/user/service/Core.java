package com.it.web.user.service;

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

    //其中map中的key是uuid生成的string类的sessionId
    public static Map<String, Tb_UserLog> userMap = new ConcurrentHashMap<>();

    //其中map中的key是uuid生成的string类的sessionId
    public static Map<String, Tb_CustomerLog> customerMap = new ConcurrentHashMap<>();

    //用户ID，用户名 缓存
    private static Map<Long, String> usernameCache = new HashMap<>();

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

        //加载用户Session
        List<Tb_UserLog> list = session.createQuery("from Tb_UserLog ").list();

        for (Tb_UserLog s : list) {
            if (now - s.getActionTime() < timeOut) {
                userMap.put(s.getSessionId(), s);
            } else {
                session.delete(s);
            }
        }

        //创建Session过期检查任务；
        ServerTask.schedulePool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Long now = System.currentTimeMillis();
                for (Map.Entry<String, Tb_UserLog> entry : userMap.entrySet()) {
                    if (now - entry.getValue().getActionTime() > timeOut) {
                        try {
                            logout(entry.getKey());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (Map.Entry<String, Tb_CustomerLog> entry : customerMap.entrySet()) {
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
        Session session = HibernateUtil.openSession();
        for (Map.Entry<String, Tb_UserLog> entry : userMap.entrySet()) {
            session.saveOrUpdate(entry.getValue());
        }
        for (Map.Entry<String, Tb_CustomerLog> entry : customerMap.entrySet()) {
            session.saveOrUpdate(entry.getValue());
        }
    }

    /**
     * 登录
     * 如果是钉钉登录，无需验证密码；
     * 返回登录的sessionId
     */
    public static String login(String loginname, String password, Boolean ddLogin) {
        Tb_UserLog log = null;
        try {
            Session session = HibernateUtil.openSession();
            Tb_User user = (Tb_User) session.createQuery("from Tb_User where loginname=:ln").setParameter("ln", loginname).uniqueResult();
            if (user == null) throw new Exception("无此用户");
            if (!user.getEnable()) throw new Exception("此用户已停用");
            if (!ddLogin) {
                if (!user.getPassword().equals(password)) throw new Exception("密码错误");
            }
            log = new Tb_UserLog();
            log.setSessionId(UUID.randomUUID().toString());
            log.setAction(CommonConstant.LOGIN);
            log.setActionTime(System.currentTimeMillis());
            log.setTb_user_id(user.getId());
            session.save(log);
            //存入map中
            userMap.put(log.getSessionId(), log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log.getSessionId();
    }

    /**
     * 用户注销；
     */
    public static void logout(String sid) {
        try {
            if (userMap.containsKey(sid)) {
                //从userMap中移除登录日志
                Tb_UserLog ul = userMap.get(sid);
                userMap.remove(sid);
                Session session = HibernateUtil.openSession();
                session.refresh(ul);
                //保存退出日志
                Tb_UserLog log = new Tb_UserLog();
                log.setTb_user_id(ul.getTb_user_id());
                log.setSessionId(sid);
                log.setAction(CommonConstant.LOGOUT);
                log.setActionTime(System.currentTimeMillis());
                session.save(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取UserSession对象；
     */
    public static Tb_UserLog getUserSession(String sid) {
        Tb_UserLog ul = null;
        try {
            if (userMap.containsKey(sid)) {
                Session session = HibernateUtil.openSession();
                ul = userMap.get(sid);
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
     * 从session中获取UserLog对象,再获取整个user对象
     */
    public static Tb_User getUser(String sid) {
        Session session = HibernateUtil.openSession();
        Tb_UserLog ul = getUserSession(sid);
        Tb_User user = (Tb_User) session.createQuery("select user from Tb_User user where user.id=:uid").setParameter("uid", ul.getTb_user_id()).uniqueResult();
        //获取用户的所有角色
        user.getRoles().addAll(listUserRoles(user.getId()));
        //获取角色下的所有权限
        for (Tb_Role role : user.getRoles()) {
            List<Tb_Auth> auths = listRoleAuth(role.getId());
            user.getAuths().addAll(auths);
        }
        //获取额外的权限
        user.getAuths().addAll(listUserAuths(user.getId()));
        return user;
    }

    /**
     * 获取用户的所有角色
     */
    public static List<Tb_Role> listUserRoles(Long userId) {
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
     * 获取拥有的角色下的权限
     */
    public static List<Tb_Auth> listRoleAuths(List<Long> roleIds) {
        Session session = HibernateUtil.openSession();
        List<Tb_Auth> auths = session.createQuery("select auth from Tb_Role_Auth ra left join Tb_Auth auth on ra.tb_auth_id=auth.id where ra.tb_role_id in (:ids)").setParameter("ids", roleIds).list();
        return auths;
    }

    /**
     * 获取额外的权限
     */
    public static List<Tb_Auth> listUserAuths(Long userId) {
        Session session = HibernateUtil.openSession();
        List<Tb_Auth> auths = session.createQuery("select auth from Tb_Auth_User au left join Tb_Auth auth on au.tb_auth_id=auth.id where au.tb_user_id=:uid").setParameter("uid", userId).list();
        return auths;
    }

    /**
     * 获取用户下的权限
     */
    public static List<String> getUserAllAuths(Tb_User user) {
        ArrayList<String> list = new ArrayList<>();
        for (Tb_Auth auth : user.getAuths()) {
            if (!list.contains(auth.getAuthname())) {
                list.add(auth.getAuthname());
            }
        }
        return list;
    }

    /**
     * 从session中获取UserLog对象；
     */
    public static Tb_UserLog getUserLog(HttpSession session) {
        return getUserSession(session.getAttribute("userSid").toString());
    }

    /**
     * 从session中获取User对象；
     */
    public static Tb_User getUser(HttpSession session) {
        Tb_User user = null;
        try {
            user = getUser(session.getAttribute("userSid").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 根据用户ID获取用户名称；
     */
    public static String getUsername(Long userId) {
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
