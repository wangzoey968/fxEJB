package com.it.dao.impl;

import com.it.dao.inter.UserDaoInter;
import com.it.entity.Tb_User;
import com.it.util.DaoUtil;
import com.it.util.UserRowMapper;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by wangzy on 2018/5/22.
 */
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDaoInter {

    //使用sessionFactory,session的方式
    @Override
    public Tb_User getUser(Integer id) {
        Session session = DaoUtil.getSession();
        Tb_User user = (Tb_User) session.createQuery("from  Tb_User where id=:id").setParameter("id", id).uniqueResult();
        return user;
    }

    @Override
    public List<Tb_User> getUsers() {
        Session session = DaoUtil.getSession();
        List<Tb_User> users=(List<Tb_User>)session.createQuery("from Tb_User ").list();
        return users;
    }

    //使用jdbcTemplate的方式
    public void getUserAll() {
        String username = "3";
        DaoUtil.initTemplate();
        JdbcTemplate template = DaoUtil.getJdbcTemplate();
        String sql = "select * from tb_user where username = ?";
        Object[] params = new Object[]{username};
        List<Tb_User> users = null;
        /**
         * 使用实现类,创建userRowMapper类实现RowMapper接口
         */
        //users = template.query(sql, params, new UserRowMapper());
        /**
         * 使用匿名内部类 
         * 如果UserRowMapper类只使用一次，单独为其创建一个类多余，可以使用匿名类 
         * 省略了书写一个实现类 
         */
        users = template.query(sql, params,
                new RowMapper<Tb_User>() {
                    @Override
                    public Tb_User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                        Tb_User user = new Tb_User();
                        user.setId(resultSet.getInt("id"));
                        user.setCreateTime(resultSet.getLong("created"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setViewer(resultSet.getBoolean("isViewer"));
                        user.setAddress(resultSet.getString("address"));
                        user.setRemark(resultSet.getString("remark"));
                        return user;
                    }
                });
        //(users != null && users.size() > 0) ? users : null;
    }

    public Tb_User insert(Tb_User user) {
        DaoUtil.initTemplate();
        JdbcTemplate template = DaoUtil.getJdbcTemplate();
        String sql = "INSERT INTO tb_user(id,username,password) VALUES(?,?,?)";
        Object[] params = new Object[]{user.getId(), user.getUsername(), user.getPhone()};
        int[] types = new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
        template.update(sql, params, types);
        return user;
    }

    public void delete(Tb_User user) {
        DaoUtil.initTemplate();
        JdbcTemplate template = DaoUtil.getJdbcTemplate();
        String sql = "DELETE FROM tb_user WHERE id = ?";
        Object[] params = new Object[]{user.getId()};
        int[] types = new int[]{Types.INTEGER};
        template.update(sql, params, types);
    }

    public Tb_User update(Tb_User user) {
        DaoUtil.initTemplate();
        JdbcTemplate template = DaoUtil.getJdbcTemplate();
        String sql = "UPDATE tb_user SET name = ?, phone = ? WHERE id = ?";
        Object[] params = new Object[]{user.getUsername(), user.getPhone(), user.getId()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
        template.update(sql, params, types);
        return user;
    }

    public List<Tb_User> findAllByUser(Long id) {
        DaoUtil.initTemplate();
        JdbcTemplate template = DaoUtil.getJdbcTemplate();
        String sql = "SELECT * FROM tb_user WHERE id = ?";
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        List users = template.query(sql, params, types, new UserRowMapper());
        return users;
    }

}
