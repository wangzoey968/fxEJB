package com.it.util.ftpServer;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigService {

    public static String getCachePath() {
        return getCfg("cachePath");
    }

    public static String getFileRoot() {
        return getCfg("fileRoot");
    }

    public static String getFtpServer() {
        return getCfg("ftpServer");
    }

    public static String getFtpLoginName() {
        return getCfg("ftpLoginName");
    }

    public static String getFtpPassword() {
        return getCfg("ftpPassword");
    }


    private static Map<String, String> cfgMap = new HashMap<>();

    public static String getCfg(String key) {
        return cfgMap.containsKey(key) ? cfgMap.get(key) : null;
    }

    public static void init() {
        try {
            SAXReader reader = new SAXReader();
            URI uri = ConfigService.class.getClass().getClassLoader().getResource("ConfigServer.xml").toURI();
            //URL url = Class.class.getClass().getResource("ConfigServer.xml");
            //File file = Paths.get(System.getProperty("user.dir") + "/src/main/java/com/it/util/ftpServer/ConfigServer.xml").toFile();
            Document document = reader.read(Paths.get(uri).toFile());
            //获取根节点元素对象
            Element root = document.getRootElement();
            List<Element> list = root.elements();
            for (Element s : list) {
                cfgMap.put(s.getName(), s.getText());
                System.out.println(s.getName() + "/" + s.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
