package com.it.util;

import com.it.entity.Tb_User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wangzy on 2018/5/24.
 */
public class UserRowMapper implements RowMapper<Tb_User>{
    @Override
    public Tb_User mapRow(ResultSet resultSet, int i) throws SQLException {
        Tb_User user = new Tb_User();
        user.setId(resultSet.getInt("id"));
        user.setCreateTime(resultSet.getLong("created"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setPhone(resultSet.getString("phone"));
        user.setAddress(resultSet.getString("address"));
        user.setViewer(resultSet.getBoolean("isViewer"));
        user.setRemark(resultSet.getString("remark"));
        return user;
    }
}
