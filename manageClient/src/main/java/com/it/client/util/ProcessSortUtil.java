package com.it.client.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.api.common.constant.ORDER_PROCESS;

import java.util.ArrayList;
import java.util.List;

public class ProcessSortUtil {

    public static Gson gson = new Gson();

    public static List<String> getProcesses(String process) {
        List<String> base = ORDER_PROCESS.getAllProcess();
        List<String> processes = gson.fromJson(process, new TypeToken<List<String>>() {
        }.getType());

        List<String> list = new ArrayList<>();
        base.forEach(e -> {
                    if (processes.contains(e)) {
                        list.add(e);
                    }
                }
        );
        return list;
    }

}
