package com.it.client.util;

import com.sun.deploy.config.ClientConfig;
import javafx.application.Platform;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigUtil {

    /**
     * 服务器地址
     */
    private static String serverAddress;
    /**
     * 服务器端口
     */
    private static Integer serverPort;
    /**
     * FTP地址
     */
    private static String ftpHost;
    /**
     * FTP端口
     */
    private static Integer ftpPort;

    /**
     * 本地文件根目录
     */
    private static Path LocalFileRoot;
    /**
     * 本地公用缓存
     */
    private static Path CommunalCache;

    /**
     * 服务器URL
     */
    public static String getServerUrl() {
        return "http://" + serverAddress + ":" + serverPort;
    }

    /**
     * 私有缓存目录
     */
    private static Path cacheDir = Paths.get(System.getProperty("user.dir") + "\\cache\\");

    public static void init() {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(Paths.get(System.getProperty("user.dir") + "/manageClient/src/main/java/com/it/client/ClientConfig.xml").toFile());

            Element serverElement = document.getRootElement().element("Server");
            serverAddress = serverElement.attribute("host").getValue();
            serverPort = Integer.valueOf(serverElement.attribute("port").getValue());

            Element ftpElement = document.getRootElement().element("Ftp");
            ftpHost = ftpElement.attribute("host").getValue();
            ftpPort = Integer.valueOf(ftpElement.attribute("port").getValue());

            Element localFileRootElement = document.getRootElement().element("LocalFileRoot");
            LocalFileRoot = Paths.get(localFileRootElement.getText().trim());

            Element CommunalCacheElement = document.getRootElement().element("CommunalCache");
            CommunalCache = Paths.get(CommunalCacheElement.getText().trim());

            if (Files.notExists(cacheDir)) Files.createDirectories(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static Integer getServerPort() {
        return serverPort;
    }

    public static String getFtpHost() {
        return ftpHost;
    }

    public static Integer getFtpPort() {
        return ftpPort;
    }

    public static Path getLocalFileRoot() {
        return LocalFileRoot;
    }

    public static Path getCommunalCache() {
        return CommunalCache;
    }

    public static Path getCacheDir() {
        return cacheDir;
    }

}