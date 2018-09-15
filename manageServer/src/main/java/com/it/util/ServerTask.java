package com.it.util;

import com.it.util.Task.CleanTask;
import com.it.util.Task.Task;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
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

    public static void runAtFixTime(Task task, String fixTime) {
        Timestamp time = Timestamp.valueOf(fixTime);
        schedulePool.schedule(task, time.getTime(), TimeUnit.MILLISECONDS);
    }

    public static void runAtFixRate(Task task, Long firstRunDelay, Long delayGap) {
        schedulePool.scheduleAtFixedRate(task, firstRunDelay, delayGap, TimeUnit.MILLISECONDS);
    }

    public static void init() {
        LocalDateTime now = LocalDateTime.now();
        //定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                /**
                 * 执行定时任务,直接new CleanTask()即可
                 */
                new CleanTask().run();
            }
        }, now.getMinute(), 1000 * 60 * 60 * 24L);
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
