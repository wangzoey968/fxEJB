package com.it.api.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtil {

    public static Gson gson = new Gson();

    public static JsonParser jsonParser = new JsonParser();

    public static Long verifyInfo(String jsonStr) {
        JsonObject json = jsonParser.parse(jsonStr).getAsJsonObject();
        return json.has("version") ? json.get("version").getAsLong() : 0;
    }

}
