package com.it.util.ftpServer;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类重要作用是连接到文件服务器ftpServer,设置文件的缓存路径cache
 */
public class FtpConfigService {

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
            /*SAXReader reader = new SAXReader();
            URI uri = FtpConfigService.class.getClass().getClassLoader().getResource("ConfigServer.xml").toURI();
            //URL url = Class.class.getClass().getResource("ConfigServer.xml");
            //File file = Paths.get(System.getProperty("user.dir") + "/src/main/java/com/it/util/ftpServer/ConfigServer.xml").toFile();
            Document document = reader.read(Paths.get(uri).toFile());
            //获取根节点元素对象
            Element root = document.getRootElement();
            List<Element> list = root.elements();
            for (Element s : list) {
                cfgMap.put(s.getName(), s.getText());
                System.out.println(s.getName() + "/" + s.getText());
            }*/
            cfgMap.put("fileRoot","E:\\FactorySys");
            cfgMap.put("cache","E:\\FactorySys\\cache");
            cfgMap.put("ftpServer","ftp://vip.zhundianyinwu.com:21");
            cfgMap.put("ftpLoginName","factory");
            cfgMap.put("ftpPassword","factory_4006085466");
            cfgMap.put("clientMustReg","false");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
