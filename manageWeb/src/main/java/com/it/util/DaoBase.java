package com.it.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DaoBase {

    private SessionFactory sessionFactory;

    private Session session;

    public DaoBase() {
    }

    public DaoBase(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Session getSession() {
        if (session == null) session = sessionFactory.getCurrentSession();
        return session;
    }

    public DaoBase setSession(Session s) {
        this.session = s;
        return this;
    }

}
