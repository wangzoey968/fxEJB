package com.it.util;

import com.it.util.ftpServer.FtpConfigService;
import com.it.util.httpClient.core.HttpClient;
import com.it.web.user.service.Core;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by wangzy on 2018/8/2.
 * 服务器启动和退出的监听
 */
@WebListener
public class ServerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Logger log = Logger.getLogger(ServerListener.class);
        log.info("服务器启动");
        HttpClient.init();
        FtpConfigService.init();
        Core.init();
        ServerTask.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Logger log = Logger.getLogger(ServerListener.class);
        log.info("服务器停止");
        Core.destroy();
        ServerTask.destroy();
        HttpClient.destroy();
    }

}
