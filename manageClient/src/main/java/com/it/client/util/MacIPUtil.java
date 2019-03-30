package com.it.client.util;

import javax.servlet.http.HttpServletRequest;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MacIPUtil {

    //获取网卡名称,再使用getDisplayName即可
    public static List<NetworkInterface> getNetworkInterfaces() {
        ArrayList<NetworkInterface> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface element = interfaces.nextElement();
                if (element.getHardwareAddress().length == 6) {
                    list.add(element);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return list;
    }

    //转换成mac,16进制的字符串
    public static String macId(byte[] hardwareAddress) {
        if (hardwareAddress == null) return null;
        String mac = "";
        for (int i = 0; i < hardwareAddress.length; i++) {
            mac += String.format("%02X%s", hardwareAddress[i], i < hardwareAddress.length - 1 ? "-" : "");
        }
        return mac;
    }

    //获取ip地址
    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

}
