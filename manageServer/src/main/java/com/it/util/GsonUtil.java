package com.it.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtil {

    public static JsonParser jsonParser = new JsonParser();

    public static Gson gson = new Gson();

    public static Gson gsonTimeFormat = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Long verifyInfo(String jsonStr) {
        JsonObject json = jsonParser.parse(jsonStr).getAsJsonObject();
        return json.has("version") ? json.get("version").getAsLong() : 0;
    }

}
