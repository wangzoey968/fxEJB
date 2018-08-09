package com.it.util.ftpServer;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类作用是连接到文件服务器ftpServer,设置文件的缓存路径cache
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
            /*cfgMap.put("fileRoot","E:\\ftSys");
            cfgMap.put("cache","E:\\ftSys\\cache");
            cfgMap.put("ftpServer","ftp://vip.ftn.com:10");
            cfgMap.put("ftpLoginName","ft");
            cfgMap.put("ftpPassword","ft_0");
            cfgMap.put("clientMustReg","false");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
