package com.it.util.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类定义了一个枚举的权限类,方便进行分配权限,避免分配的时候手动输入错误
 */
public class AuthList {

    public static List<String> auths = new ArrayList<>();

    static {
        //订单
        auths.add("超管");
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
