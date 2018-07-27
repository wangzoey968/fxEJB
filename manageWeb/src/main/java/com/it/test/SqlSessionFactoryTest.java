package com.it.test;/*import com.it.entity.Tb_User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;*/
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * Created by wangzy on 2018/4/18.
 * for mybatis
 */
public class SqlSessionFactoryTest {

    /*private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;

    @Before
    public void init() throws Exception {
        //1
        String resource = "/config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream("database.properties");
        properties.load(in);
        String driver = properties.getProperty("jdbc.driver");
        Connection connection = DriverManager.getConnection(null, null, null);
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM tb_user");
        connection.commit();
        connection.rollback();
    }

    @After
    public void destroy() throws Exception {
        session.commit();
        session.close();
    }

    @Test
    public void getUsers() throws Exception {
        session = sqlSessionFactory.openSession();
        List<Tb_User> list = session.selectList("com.it.dao.mybatis.inter.UserDao.select");

    }*/
}
