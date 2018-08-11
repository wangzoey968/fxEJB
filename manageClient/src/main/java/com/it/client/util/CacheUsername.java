package com.it.client.util;

import com.it.client.EJB;

import java.util.HashMap;
import java.util.Map;

/**
 * 当需要显示userid的用户名时,可以直接从此类获取
 */
public class CacheUsername {

    private static Map<Long, String> usernameCache = new HashMap<>();

    public static String getUsername(Long userId) {
        try {
            if (!usernameCache.containsKey(userId)) {
                usernameCache.put(userId, EJB.getUserService().getUsernameByUserId(userId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usernameCache.get(userId);
    }

}
