package com.it.api.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wangzy on 2019/3/20.
 */
public class ORDER_TYPE {

    public static String MING_PIAN = "名片";
    public static String BU_GAN_JIAO = "不干胶";
    public static String DAN_YE = "单页";
    public static String HUA_CE = "画册";

    public static List<String> getAllTypes() {
        return Arrays.asList(MING_PIAN, BU_GAN_JIAO, DAN_YE, HUA_CE);
    }

}
