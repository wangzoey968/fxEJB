package com.it.client.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by wangzy on 2018/6/12.
 */
public class TaskUtil {

    public static ScheduledExecutorService taskPool = Executors.newScheduledThreadPool(10);

}
