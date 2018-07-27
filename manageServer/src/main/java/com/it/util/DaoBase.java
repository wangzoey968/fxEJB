package com.it.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DaoBase {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    Session getSession() {
        if (session == null) session = sessionFactory.getCurrentSession();
        return session;
    }

    DaoBase setSession(Session s) {
        this.session = s;
        return this;
    }

}
