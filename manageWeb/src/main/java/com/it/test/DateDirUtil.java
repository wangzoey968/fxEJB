package com.it.test;

import java.time.LocalDate;

/**
 * Created by wangzy on 2019/3/23.
 */
public class DateDirUtil {

    public static String getYear() {
        return Integer.toString(LocalDate.now().getYear());
    }

    public static String getMonthShort() {
        return Integer.toString(LocalDate.now().getMonthValue());
    }

    public static String getMonthLong() {
        int month = LocalDate.now().getMonthValue();
        String ret = "";
        if (month < 10) ret = "0";
        ret += month;
        return ret;
    }

    public static String getDay() {
        return Integer.toString(LocalDate.now().getDayOfMonth());
    }

    public static final String disk = "E:/testFile";

    public static String getRootDir() {
        return disk + "/" + getYear() + "/" + getMonthLong() + "/" + getDay();
    }

}
