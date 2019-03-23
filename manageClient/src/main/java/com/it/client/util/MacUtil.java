package com.it.client.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MacUtil {

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

}
