package com.it.client.util.httpClient.core;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HttpClient {

    //Http连接池;
    private static PoolingHttpClientConnectionManager conManage = new PoolingHttpClientConnectionManager();
    //Cookie
    private static CookieStore cookieStore = new BasicCookieStore();

    private static CloseableHttpClient httpClient = null;

    private static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(40);

    static void Init() {
        conManage.setMaxTotal(40);
        conManage.setDefaultMaxPerRoute(40);
        httpClient = HttpClients.custom().setConnectionManager(conManage).setDefaultCookieStore(cookieStore).build();
    }

    static void Destroy() {
        schedule.shutdown();
    }


    static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    static ScheduledExecutorService getSes() {
        return schedule;
    }

}
