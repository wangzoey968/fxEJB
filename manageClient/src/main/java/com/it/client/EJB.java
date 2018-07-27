package com.it.client;

import com.it.api.OrderServiceInter;
import com.it.api.UserServiceInter;
import com.it.client.util.ConfigUtil;
import com.it.client.util.TaskUtil;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

public class EJB {

    public static StringProperty sidProperty = new SimpleStringProperty(null);

    public static String getSid() {
        return sidProperty.get();
    }

    public static LongProperty userIdProperty = new SimpleLongProperty();

    public static Long getUserId() {
        return userIdProperty.get();
    }

    public static StringProperty factoryNameProperty = new SimpleStringProperty("Login");
    public static StringProperty userNameProperty = new SimpleStringProperty("");

    public static String getUserName() {
        return userNameProperty.get();
    }

    private static Context context = null;
    //1000 * 60 * 2L;
    private final static Long testTime =  1000 * 60 * 2L;      //测试时间

    static {
        try {
            final Hashtable jndiProp = new Hashtable();
            jndiProp.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProp.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            jndiProp.put("remote.connection.default.host", ConfigUtil.getServerAddress());
            jndiProp.put("remote.connection.default.port", ConfigUtil.getServerPort());
            //jndiProp.put("remote.connection.default.username", "wangzoey911");
            //jndiProp.put("remote.connection.default.password", "lighter110");
            context = new InitialContext(jndiProp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static OrderServiceInter orderService = null;
    private static Long orderServiceTime = 0L;

    public static OrderServiceInter getOrderService() {
        orderServiceTime = System.currentTimeMillis();
        try {
            //orderService.test();
            orderService = (OrderServiceInter) context.lookup("java:manageServer//OrderServiceRemote!com.it.api.OrderServiceInter");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderService;
    }

    private static UserServiceInter userService = null;
    private static Long userServiceTime = 0L;

    public static UserServiceInter getUserService() {
        userServiceTime = System.currentTimeMillis();
        try {
            //userService.test();
            userService = (UserServiceInter) context.lookup("java:manageServer//UserServiceRemote!com.it.api.UserServiceInter");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userService;
    }

    public static void startup() {
        getUserService();
        TaskUtil.taskPool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Long now = System.currentTimeMillis();
                    if (orderService != null && (now - orderServiceTime > testTime)) getOrderService();
                    //......
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, testTime, 1000L, TimeUnit.MILLISECONDS);
    }

}
