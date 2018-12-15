package com.it.client;

import com.it.api.CoreServiceLocal;
import com.it.api.OrderServiceLocal;
import com.it.api.SupplierServiceLocal;
import com.it.api.UserServiceLocal;
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

    /**
     * 设置客户端session
     */
    private static StringProperty sessionId = new SimpleStringProperty(null);

    public static void setSessionId(String sessionId) {
        EJB.sessionId.set(sessionId);
    }

    public static String getSessionId() {
        return sessionId.get();
    }

    /**
     * 设置客户端userId
     */
    private static LongProperty userId = new SimpleLongProperty();

    public static void setUserId(long userId) {
        EJB.userId.set(userId);
    }

    public static Long getUserId() {
        return userId.get();
    }

    /**
     * 设置客户端username
     */
    private static StringProperty username = new SimpleStringProperty("");

    public static void setUsername(String username) {
        EJB.username.set(username);
    }

    public static String getUsername() {
        return username.get();
    }

    /**
     * 向下为ejb的配置
     */
    private static Context context = null;
    //1000 * 60 * 2L;
    private final static Long testTime = 1000 * 60 * 2L;      //测试时间

    static {
        System.out.println("----------------init ejb container----------------");
        try {
            final Hashtable jndiProp = new Hashtable();
            jndiProp.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProp.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            jndiProp.put("remote.connection.default.host", ConfigUtil.getServerAddress());
            jndiProp.put("remote.connection.default.port", ConfigUtil.getServerPort());
            //使用jboss的security时候用的到
            //jndiProp.put("remote.connection.default.username", "wangzoey911");
            //jndiProp.put("remote.connection.default.password", "lighter110");
            context = new InitialContext(jndiProp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static OrderServiceLocal orderService = null;
    private static Long orderServiceTime = 0L;

    public static OrderServiceLocal getOrderService() {
        orderServiceTime = System.currentTimeMillis();
        try {
            orderService = (OrderServiceLocal) context.lookup("java:manageServer//OrderServiceRemote!com.it.api.OrderServiceLocal");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderService;
    }

    private static UserServiceLocal userService = null;
    private static Long userServiceTime = 0L;

    public static UserServiceLocal getUserService() {
        userServiceTime = System.currentTimeMillis();
        try {
            userService = (UserServiceLocal) context.lookup("java:manageServer//UserServiceRemote!com.it.api.UserServiceLocal");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userService;
    }

    private static CoreServiceLocal coreService = null;
    private static Long coreServiceTime = 0L;

    public static CoreServiceLocal getCoreService() {
        coreServiceTime = System.currentTimeMillis();
        try {
            coreService = (CoreServiceLocal) context.lookup("java:manageServer//CoreServiceRemote!com.it.api.CoreServiceLocal");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coreService;
    }

    private static SupplierServiceLocal supplierService = null;
    private static Long supplierServiceTime = 0L;

    public static SupplierServiceLocal getSupplierService() {
        supplierServiceTime = System.currentTimeMillis();
        try {
            supplierService = (SupplierServiceLocal) context.lookup("java:manageServer//SupplierServiceRemote!com.it.api.SupplierServiceLocal");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierService;
    }

    public static void startup() {
        getCoreService();
        TaskUtil.taskPool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Long now = System.currentTimeMillis();
                    if (userService != null && (now - userServiceTime > testTime)) getUserService();
                    if (orderService != null && (now - orderServiceTime > testTime)) getOrderService();
                    if (supplierService != null && (now - supplierServiceTime > testTime)) getSupplierService();
                    //......
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, testTime, 1000L, TimeUnit.MILLISECONDS);
    }

}
