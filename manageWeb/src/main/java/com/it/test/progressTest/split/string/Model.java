package com.it.test.progressTest.split.string;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wangzy on 2019/3/14.
 */
public class Model {

    private Worker<String> worker;
    private AtomicBoolean shouldThrow = new AtomicBoolean(false);

    public Model() {
        worker = new Task<String>() {
            @Override
            protected String call() throws Exception {
                updateTitle("Example Task");
                updateMessage("Starting...");
                final int total = 250;
                updateProgress(0, total);
                for (int i = 1; i <= total; i++) {
                    if (isCancelled()) {
                        updateValue("Canceled at" + System.currentTimeMillis());
                        return null;
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        updateValue("Canceled at " + System.currentTimeMillis());
                        return null;
                    }
                    if (shouldThrow.get()) {
                        throw new RuntimeException("Exception thrown at " + System.currentTimeMillis());
                    }
                    updateTitle("Example Task(" + i + ")");
                    updateMessage("Processed " + i + " of " + total + " items.");
                    updateProgress(i, total);
                }
                return "Completed at " + System.currentTimeMillis();
            }

            @Override
            protected void scheduled() {
                System.out.println("The task is scheduled.");
            }

            @Override
            protected void running() {
                System.out.println("The task is running.");
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("my succeed");
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                System.out.println("my canceled");
            }

            @Override
            protected void failed() {
                super.failed();
                System.out.println("my failed");
            }
        };
    }

    public Worker<String> getWorker() {
        return worker;
    }

    public void setWorker(Worker<String> worker) {
        this.worker = worker;
    }

    public AtomicBoolean getShouldThrow() {
        return shouldThrow;
    }

    public void setShouldThrow(AtomicBoolean shouldThrow) {
        this.shouldThrow = shouldThrow;
    }
}
