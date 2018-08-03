package com.it.util.Task;

/**
 * 设计此类,继承抽象的task,进行定时清扫
 */
public class CleanTask extends Task {

    @Override
    public void run() {
        // TODO 对数据库进行相关的操作
        System.out.println("进行定时清扫");
    }

}
