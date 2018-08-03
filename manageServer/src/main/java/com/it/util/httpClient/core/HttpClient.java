package com.it.util.httpClient.core;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HttpClient {

    //Http连接池;
    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    //Cookie
    private static CookieStore cookieStore = new BasicCookieStore();

    private static CloseableHttpClient httpClient = null;

    private static ScheduledExecutorService ses = Executors.newScheduledThreadPool(40);

    public static void init() {
        cm.setMaxTotal(40);
        cm.setDefaultMaxPerRoute(40);
        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultCookieStore(cookieStore).build();
    }


    public static void destroy() {
        ses.shutdown();
    }


    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static ScheduledExecutorService getSes() {
        return ses;
    }

}
