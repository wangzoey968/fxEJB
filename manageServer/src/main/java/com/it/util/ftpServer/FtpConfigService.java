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
            //FtSys
            /*cfgMap.put("fileRoot","E:\\ft");
            cfgMap.put("cache","E:\\ft\\cache");
            cfgMap.put("ftpServer","ftp://vip.ft.com:8080");
            cfgMap.put("ftpLoginName","f");
            cfgMap.put("ftpPassword","fx_0");
            cfgMap.put("clientMustReg","false");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
