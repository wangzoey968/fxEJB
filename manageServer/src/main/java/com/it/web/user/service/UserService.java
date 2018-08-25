package com.it.web.user.service;

import com.it.api.table.Tb_Computer;
import com.it.api.table.user.Tb_Auth;
import com.it.api.table.user.Tb_Role;
import com.it.api.table.user.Tb_User;
import com.it.api.table.user.Tb_User_Role;
import com.it.util.HibernateUtil;
import com.it.web.user.dao.UserDao;
import org.hibernate.Session;
import org.junit.Test;

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
        System.out.println(roles.toString() + "/roles");
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
        session.update(role);
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
    public static List<Tb_Auth> listAuth(Tb_User user, Long userId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        List<Tb_Auth> list = session.createQuery("select auth from Tb_Auth auth left join Tb_User_Auth au on auth.id=au.tb_auth_id where au.tb_user_id=:uid")
                .setParameter("uid", userId).list();
        return list;
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
        session.update(auth);
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
    public static List<Tb_Auth> listRoleAuth(Tb_User user, Long roleId) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        List<Tb_Auth> list = session.createQuery("select auth from Tb_Role_Auth ra left join Tb_Auth  auth on ra.tb_role_id=auth.id where ra.tb_role_id=:id").setParameter("id", roleId).list();
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
        if (ur==null)throw new Exception("不存在");
        session.delete(ur);
        session.getTransaction().commit();
    }

    @Test
    public void ss() {
        Long uid = 1L;
        HibernateUtil.initSessionFactory();
        Session session = HibernateUtil.openSession();
        List list = session.createQuery("select user,auth from Tb_User user left join Tb_User_Auth  au on user.id=au.tb_user_id left join Tb_Auth  auth on au.tb_auth_id=auth.id where user.id=:id")
                .setParameter("id", uid).list();
        Tb_User u = null;
        List<Tb_Role> rs = session.createQuery("select role from Tb_User_Role  ur left join Tb_Role role on ur.tb_role_id=role.id where ur.tb_user_id=:uid").setParameter("uid", uid).list();
        for (Tb_Role role : rs) {
            List<Tb_Auth> as = session.createQuery("select auth from Tb_Auth auth left join Tb_Role_Auth ra on ra.tb_auth_id=auth.id where ra.tb_role_id=:rid").setParameter("rid", role.getId()).list();
            //角色下的权限
            role.setAuths(as);
        }
        for (Object o : list) {
            Object[] objects = (Object[]) o;
            Tb_User user = (Tb_User) objects[0];
            Tb_Auth auth = (Tb_Auth) objects[1];
            u = user;
            //扩展的权限

            u.getAuths().add(auth);
        }
        u.setRoles(rs);
        System.out.println(u.toString());
    }

}
