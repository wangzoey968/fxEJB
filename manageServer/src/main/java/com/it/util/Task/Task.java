package com.it.util.Task;

import com.it.util.ServerTask;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

/**
 * 此类为abstract类,可以设计一个类比如CleanTask继承此类,进行定时清扫
 */
public abstract class Task implements Runnable, Serializable {

    public ScheduledFuture runLater() {
        //ServerTask.runLater(this);
        return null;
    }

}
