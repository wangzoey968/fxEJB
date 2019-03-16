package com.it.test;

/**
 * Created by wangzy on 2018/4/29.
 */
public class MyThread implements Runnable {
    @Override
    public void run() {
        try {
            int i = 0;
            while (i < 3) {
                System.out.println("11111");
                Thread.sleep(1000);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.run();
    }

}
