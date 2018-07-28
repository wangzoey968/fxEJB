package com.it.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.nio.file.Paths;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    private static Session session = null;
    private static JdbcTemplate jdbcTemplate = null;

    //使用sessionFactory,测试专用
    public static void initSessionFactory() {
        //此处需要加载两个配置文件,如果不加载.cfg.xml文件,会提示,无entity的实体映射
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .configure(Paths.get("src/main/resources/hibernate.test.xml").toFile())
                .configure(Paths.get("src/main/resources/hibernate.cfg.xml").toFile());
        StandardServiceRegistry registry = builder.build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
    }

    //使用jdbcTemplate,测试专用
    public static void initTemplate() {
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/ssh");
        source.setUsername("root");
        source.setPassword("zhundian66");
        jdbcTemplate = new JdbcTemplate(source);
    }

    public HibernateUtil(SessionFactory sf) {
        sessionFactory = sf;
    }

    public HibernateUtil(JdbcTemplate template) {
        jdbcTemplate = template;
    }

    public HibernateUtil(SessionFactory sf, JdbcTemplate template) {
        sessionFactory = sf;
        jdbcTemplate = template;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public static Session openSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

}
