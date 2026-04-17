package com.java.concept.multithreading.threadpools;

import java.util.concurrent.*;

public class ThreadPoolComparison {

    public static void main(String[] args) throws InterruptedException {
        // 1. Fixed Thread Pool: Only 2 threads will ever run, regardless of task count
        System.out.println("--- Starting Fixed Thread Pool (Size: 2) ---");
        runTest(Executors.newFixedThreadPool(2));

        // 2. Cached Thread Pool: Will create 5 threads instantly for 5 tasks
        System.out.println("\n--- Starting Cached Thread Pool ---");
        runTest(Executors.newCachedThreadPool());

        // 3. Single Thread Executor: Tasks will run one after another (order 1 to 5)
        System.out.println("\n--- Starting Single Thread Executor ---");
        runTest(Executors.newSingleThreadExecutor());
    }

    private static void runTest(ExecutorService executor) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Task " + taskId + " running on " + threadName);
                try {
                    // Simulate work
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();
    }
}
