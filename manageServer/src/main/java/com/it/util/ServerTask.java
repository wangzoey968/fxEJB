package com.it.util;

import com.it.util.Task.CleanTask;
import javafx.concurrent.Task;
import jdk.nashorn.internal.runtime.logging.Logger;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class ServerTask {

    public static ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(10);

    public static Timer timer = new Timer();

    public static Map<ScheduledFuture, Task> taskMap = new ConcurrentHashMap<ScheduledFuture, Task>();

    static {
        schedulePool.schedule(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Iterator<Map.Entry<ScheduledFuture, Task>> iterator = taskMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<ScheduledFuture, Task> next = iterator.next();
                            if (next.getKey().isDone() || next.getKey().isCancelled()) {
                                iterator.remove();
                            }
                        }
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture runLater(Task task) {
        ScheduledFuture<?> res = schedulePool.schedule(task, 0, TimeUnit.MILLISECONDS);
        taskMap.put(res, task);
        return res;
    }

    public static void init() {
        LocalDateTime now = LocalDateTime.now();
        //定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new CleanTask().run();
            }
        }, now.getMinute(), 1000 * 60L);
    }

    public static void destroy() {
        try {
            timer.cancel();
            while (!taskMap.isEmpty()) {
                Thread.sleep(500);
            }
            schedulePool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
