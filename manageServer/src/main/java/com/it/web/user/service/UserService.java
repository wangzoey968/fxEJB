package com.it.web.user.service;

import com.it.api.table.Tb_Computer;
import com.it.api.table.user.*;
import com.it.util.HibernateUtil;
import com.it.web.user.dao.UserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    /**
     * 插入用户
     */
    public static Tb_User insertUser(Tb_User user, Tb_User newUser) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您没有用户管理权限");
        UserDao dao = new UserDao();
        dao.insertUser(newUser);
        return newUser;
    }

    /**
     * 修改用户
     */
    public static Tb_User updateUser(Tb_User user, Tb_User updateUser) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您没有用户管理权限");
        UserDao dao = new UserDao();
        dao.updateUser(updateUser);
        return updateUser;
    }

    /**
     * 删除用户
     */
    public static void deleteUser(Tb_User user, Long userId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您没有用户管理权限");
        UserDao dao = new UserDao();
        dao.deleteUser(userId);
    }

    /**
     * 查询用户
     */
    public static List<Tb_User> listUser(Tb_User user, String key) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您没有管理权限");
        UserDao dao = new UserDao();
        return dao.listUser(key);
    }


    /**
     * 设置为允许或禁止登录
     */
    public static Tb_User setUserEnable(Tb_User user, Long userId, boolean isEnable) throws Exception {
        if (!Core.getUserAllAuths(user).contains("用户管理")) throw new Exception("您没有用户管理权限");
        UserDao dao = new UserDao();
        return dao.isEnableUserLogin(userId, isEnable);
    }

    public static List<Tb_Computer> findComputerInMacs(List<String> macs) {
        Session session = HibernateUtil.openSession();
        List<Tb_Computer> list = session.createQuery("from Tb_Computer where macId in (:macs)").setParameter("macs", macs).list();
        return list;
    }

    public static List<Tb_Computer> listComputer(String keyword) {
        Session session = HibernateUtil.openSession();
        List<Tb_Computer> list = session.createQuery("FROM Tb_Computer WHERE computerName like :keyword OR macId like :keyword")
                .setParameter("keyword", "%" + keyword + "%")
                .list();
        return list;
    }

    public static void regMac(String macId, String computerName, String loginName, String password) throws Exception {
        if (macId == null) throw new Exception("网卡错误；");
        if (computerName == null || computerName.isEmpty()) throw new Exception("请填写注册名称");
        if (loginName == null || loginName.isEmpty()) throw new Exception("请填写用户名");
        if (password == null || password.isEmpty()) throw new Exception("请填写密码");
        Session session = HibernateUtil.openSession();
        Tb_User user = (Tb_User) session.createQuery("FROM Tb_User WHERE loginname=:loginName")
                .setParameter("loginName", loginName)
                .uniqueResult();
        if (user == null) throw new Exception("手机号未注册");
        if (user.getPassword().equals(password)) throw new Exception("密码错误");
        Tb_Computer cmp = new Tb_Computer(computerName, macId, user.getUsername().equals("管理员"), user.getId(), null);
        session.save(cmp);
    }

    public static Tb_Computer updateComputer(Tb_User user, Tb_Computer comp) throws Exception {
        if (user.getUsername().equals("管理员")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        Tb_Computer computer = new Tb_Computer(comp.getComputerName(), comp.getMacId(), comp.getReg(), comp.getRegUserId(), comp.getNote());
        session.update(computer);
        return computer;
    }

    public static void deleteComputer(Tb_User user, Long computerId) throws Exception {
        if (user.getUsername().equals("管理员")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        Tb_Computer computer = (Tb_Computer) session.createQuery("from Tb_Computer where id=:id").setParameter("id", computerId).uniqueResult();
        if (computer == null) throw new Exception("已删除,请刷新");
        session.delete(computer);
    }

    //*****************************************角色相关操作
    public static List<Tb_Role> listUserRole(Tb_User user, Long userId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        List<Tb_Role> roles = session.createQuery("select role from Tb_Role role left join Tb_User_Role ur on role.id=ur.tb_role_id where ur.tb_user_id=:uid")
                .setParameter("uid", userId).list();
        return roles;
    }

    public static Tb_Role addRole(Tb_User user, Tb_Role role) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        session.save(role);
        session.getTransaction().commit();
        return role;
    }

    public static Tb_Role updateRole(Tb_User user, Tb_Role role) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        Tb_Role r = (Tb_Role) session.createQuery("from Tb_Role where id=:id").setParameter("id", role.getId()).uniqueResult();
        if (r == null) throw new Exception("请刷新");
        //session.merge(role);
        session.update(r);
        session.getTransaction().commit();
        return role;
    }

    public static void deleteRole(Tb_User user, Long roleId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        Tb_Role r = (Tb_Role) session.createQuery("from Tb_Role where id=:id").setParameter("id", roleId).uniqueResult();
        if (r == null) throw new Exception("请刷新");
        session.delete(r);
        session.getTransaction().commit();
    }

    public static List<Tb_Role> listAllRole(Tb_User user) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        List<Tb_Role> list = session.createQuery("from Tb_Role").list();
        return list;
    }

    //***************************************auth相关操作
    public static List<Tb_Auth> listUserAuth(Tb_User user, Long userId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        List list = session.createQuery("select auth ,ua from Tb_Auth auth left join Tb_User_Auth ua on ua.tb_auth_id=auth.id where ua.tb_user_id=:uid")
                .setParameter("uid", userId).list();
        ArrayList<Tb_Auth> auths = new ArrayList<>();
        for (Object o : list) {
            Object[] objects = (Object[]) o;
            Tb_Auth auth = (Tb_Auth) objects[0];
            Tb_User_Auth ua = (Tb_User_Auth) objects[1];
            auth.setExtend(ua.getExtend());
            auths.add(auth);
        }
        return auths;
    }

    public static Tb_Auth addRole1Auth(Tb_User user, Long roleId, Tb_Auth auth) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        session.save(auth);
        Tb_Role_Auth ra = new Tb_Role_Auth();
        ra.setTb_role_id(roleId);
        ra.setTb_auth_id(auth.getId());
        session.save(ra);
        session.getTransaction().commit();
        return auth;
    }

    public static Tb_Auth addAuth(Tb_User user, Tb_Auth auth) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        session.save(auth);
        session.getTransaction().commit();
        return auth;
    }

    public static Tb_Auth updateAuth(Tb_User user, Tb_Auth auth) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        Tb_Auth a = (Tb_Auth) session.createQuery("from Tb_Auth where id=:id").setParameter("id", auth.getId()).uniqueResult();
        if (a == null) throw new Exception("请刷新");
        //session.merge(auth);
        session.update(a);
        session.getTransaction().commit();
        return auth;
    }

    public static void deleteAuth(Tb_User user, Long authId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        Tb_Auth a = (Tb_Auth) session.createQuery("from Tb_Auth where id=:id").setParameter("id", authId).uniqueResult();
        if (a == null) throw new Exception("请刷新");
        session.delete(a);
        session.getTransaction().commit();
    }


    //****************************************
    public static List<Tb_Auth> listRoleAuth(Tb_User user, Long userId, Long roleId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        List<Tb_Auth> list = session.createQuery("select auth from Tb_Role_Auth ra left join Tb_Auth auth on ra.tb_auth_id=auth.id where ra.tb_role_id=:rid")
                .setParameter("rid", roleId).list();
        for (Tb_Auth auth : list) {
            auth.setExtend(true);
        }
        return list;
    }

    //为用户分配角色
    public static Tb_User_Role addUserRole(Tb_User user, Long userId, Long roleId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        Tb_User_Role ur = (Tb_User_Role) session.createQuery("from Tb_User_Role where tb_user_id=:uid and tb_role_id=:rid").setParameter("uid", userId).setParameter("rid", roleId).uniqueResult();
        if (ur != null) throw new Exception("已存在");
        Tb_User_Role u = new Tb_User_Role();
        u.setTb_user_id(userId);
        u.setTb_role_id(roleId);
        session.save(u);
        session.getTransaction().commit();
        return u;
    }

    //删除用户角色
    public static void deleteUserRole(Tb_User user, Long userId, Long roleId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("不是超管");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        Tb_User_Role ur = (Tb_User_Role) session.createQuery("from Tb_User_Role where tb_user_id=:uid and tb_role_id=:rid").setParameter("uid", userId).setParameter("rid", roleId).uniqueResult();
        if (ur == null) throw new Exception("不存在");
        session.delete(ur);
        session.getTransaction().commit();
        deleteUserExtendExclude(userId);
    }

    public static void deleteUserExtendExclude(Long userId) throws Exception {
        Session session = HibernateUtil.openSession();
        List<Tb_User_Auth> list = session.createQuery("from Tb_User_Auth where tb_user_id=:uid").setParameter("uid", userId).list();
        session.getTransaction().begin();
        for (Tb_User_Auth ua : list) {
            session.delete(ua);
        }
        session.getTransaction().commit();
    }

    //为角色分配权限
    public static Tb_Role_Auth addRoleAuth(Tb_User user, Long roleId, Long authId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是超管");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        Tb_Role_Auth ra = (Tb_Role_Auth) session.createQuery("from Tb_Role_Auth where tb_role_id=:rid and tb_auth_id =:aid")
                .setParameter("rid", roleId).setParameter("aid", authId).uniqueResult();
        if (ra != null) throw new Exception("已存在");
        Tb_Role_Auth r = new Tb_Role_Auth();
        r.setTb_role_id(roleId);
        r.setTb_auth_id(authId);
        session.save(r);
        session.getTransaction().commit();
        return r;
    }

    //删除角色下的权限
    public static void deleteRoleAuth(Tb_User user, Long roleId, Long authId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是超管");
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        Tb_Role_Auth ra = (Tb_Role_Auth) session.createQuery("from Tb_Role_Auth where tb_role_id=:rid and tb_auth_id =:aid")
                .setParameter("rid", roleId).setParameter("aid", authId).uniqueResult();
        if (ra == null) throw new Exception("不存在");
        session.delete(ra);
        session.getTransaction().commit();
    }

    //添加扩展或限制的用户权限
    public static Tb_User_Auth addUserAuth(Tb_User user, Long userId, Long authId, Boolean isExtend) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是超管");
        Session session = HibernateUtil.openSession();
        Tb_User_Auth ua = (Tb_User_Auth) session.createQuery("from Tb_User_Auth where tb_user_id=:uid and tb_auth_id=:aid")
                .setParameter("uid", userId).setParameter("aid", authId).uniqueResult();
        if (ua != null) throw new Exception("已存在");
        session.getTransaction().begin();
        Tb_User_Auth userAuth = new Tb_User_Auth();
        userAuth.setTb_user_id(userId);
        userAuth.setTb_auth_id(authId);
        userAuth.setExtend(isExtend);
        session.save(userAuth);
        session.getTransaction().commit();
        return userAuth;
    }

    //删除扩展或限制的用户权限
    public static void deleteUserAuth(Tb_User user, Long userId, Long authId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是超管");
        Session session = HibernateUtil.openSession();
        Tb_User_Auth userAuth = (Tb_User_Auth) session.createQuery("from Tb_User_Auth where tb_user_id=:uid and tb_auth_id=:aid")
                .setParameter("uid", userId).setParameter("aid", authId).uniqueResult();
        if (userAuth == null) throw new Exception("不存在");
        session.getTransaction().begin();
        session.delete(userAuth);
        session.getTransaction().commit();
    }

    public static Tb_User login(String username, String password) throws Exception {
        Session session = HibernateUtil.openSession();
        Tb_User user = (Tb_User) session.createQuery("from Tb_User where loginname=:un").setParameter("un", username).uniqueResult();
        if (user == null) throw new Exception("用户不存在");
        if (!user.getPassword().equals(DigestUtils.md5Hex(password))) {
            throw new Exception("密码或用户名输入错误");
        }
        return user;
    }

}
