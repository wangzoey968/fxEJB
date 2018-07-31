package com.it.web.user.service;

import java.util.HashSet;
import java.util.Set;

public class AuthList {

    public static Set<String> auths = new HashSet<>();

    public static void authAdd() {
        //订单
        auths.add("显示订单菜单");
        auths.add("显示工作安排");
        auths.add("订单查询");
        auths.add("返工");
        auths.add("安排加工");
        auths.add("安排外协");
        auths.add("显示零件合版");
        auths.add("零件合版");
        auths.add("显示成品打包");
        auths.add("成品打包");
        auths.add("零件合版");
    }

}
