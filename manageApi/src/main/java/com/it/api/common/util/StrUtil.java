package com.it.api.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangzy on 2019/4/8.
 */
public class StrUtil {

    public static List<String> toList(String str) {
        if (str.length() == 0) return null;
        String sub = str.substring(1, str.length() - 1);
        String[] split = sub.split(", ");
        return Arrays.asList(split);
    }

}
