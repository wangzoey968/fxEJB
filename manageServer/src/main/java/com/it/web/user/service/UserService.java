package com.it.web.user.service;

import com.it.api.table.Tb_Computer;
import com.it.api.table.user.Tb_User;
import com.it.util.HibernateUtil;
import com.it.web.user.dao.UserDao;
import org.hibernate.Session;

import java.util.List;

public class UserService {

    /**
     * 插入用户
     */
    public static Tb_User insertUser(Tb_User mySelf, Tb_User newUser) throws Exception {
        if (!mySelf.getAuths().contains("用户管理")) throw new Exception("您没有用户管理权限");
        UserDao dao = new UserDao();
        dao.insertUser(newUser);
        return newUser;
    }

    /**
     * 修改用户
     */
    public static Tb_User updateUser(Tb_User mySelf, Tb_User updateUser) throws Exception {
        if (!mySelf.getAuths().contains("用户管理")) throw new Exception("您没有用户管理权限");
        UserDao dao = new UserDao();
        dao.updateUser(updateUser);
        return updateUser;
    }

    /**
     * 查询用户
     */
    public static List<Tb_User> listUsers(Tb_User mySelf, String name) {
        UserDao dao = new UserDao();
        return dao.listUsers(name);
    }

    ;

    /**
     * 允许或禁止登录
     */
    public static Tb_User setUserEnable(Tb_User mySelf, Long userId, boolean isEnable) throws Exception {
        if (!mySelf.getAuths().contains("用户管理")) throw new Exception("您没有用户管理权限");
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

    public static Tb_Computer updateComputer(Tb_User mySelf, Tb_Computer comp) throws Exception {
        if (mySelf.getUsername().equals("管理员")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        Tb_Computer computer = new Tb_Computer(comp.getComputerName(), comp.getMacId(), comp.getReg(), comp.getRegUserId(), comp.getNote());
        session.update(computer);
        return computer;
    }

    public static void deleteComputer(Tb_User mySelf, Long computerId) throws Exception {
        if (mySelf.getUsername().equals("管理员")) throw new Exception("您不是管理员");
        Session session = HibernateUtil.openSession();
        Tb_Computer computer = (Tb_Computer) session.createQuery("from Tb_Computer where id=:id").setParameter("id", computerId).uniqueResult();
        if (computer == null) throw new Exception("已删除,请刷新");
        session.delete(computer);
    }

}
