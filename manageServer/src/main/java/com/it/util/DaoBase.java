package com.it.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DaoBase {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session;

    public Session getSession() {
        if (session == null) session = sessionFactory.getCurrentSession();
        return session;
    }

    public DaoBase setSession(Session s) {
        this.session = s;
        return this;
    }

}
