package com.it.api.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wangzy on 2019/3/22.
 */
public class ORDER_PROCESS {

    public static String XIA_DAN = "下单";
    public static String ZHI_BAN = "制版";
    public static String YIN_SHUA = "印刷";
    public static String MO_QIE = "模切";
    public static String YA_HEN = "压痕";
    public static String YA_CHI = "压齿";
    public static String FU_MO = "覆膜";
    public static String CAI_QIE = "裁切";
    public static String DA_MA = "打码";
    public static String YUAN_JIAO = "圆角";

    public static List<String> getAllProcess() {
        return Arrays.asList(XIA_DAN, ZHI_BAN, YIN_SHUA, MO_QIE, YA_HEN, YA_CHI, FU_MO, CAI_QIE, DA_MA, YUAN_JIAO);
    }

}
