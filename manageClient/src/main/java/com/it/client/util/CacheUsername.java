package com.it.client.util;

import com.it.client.EJB;

import java.util.HashMap;
import java.util.Map;

public class CacheUsername {

    private static Map<Long, String> userNameCache = new HashMap<>();

    public static String getUserName(Long userId) {
        try {
            if (!userNameCache.containsKey(userId)) {
                userNameCache.put(userId, EJB.getUserService().getUserNameByUserId(userId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userNameCache.get(userId);
    }

}
