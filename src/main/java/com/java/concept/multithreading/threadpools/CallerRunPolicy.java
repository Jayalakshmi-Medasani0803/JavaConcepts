package com.java.concept.multithreading.threadpools;

import java.util.concurrent.*;

public class CallerRunPolicy {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                      // Core: 2 threads always stay alive
                5,                      // Max: Can grow to 5 threads
                60, TimeUnit.SECONDS,   // Keep-alive for extra threads
                new ArrayBlockingQueue<>(10), // Queue: Holds 10 tasks
                new ThreadPoolExecutor.CallerRunsPolicy() // Strategy if pool is FULL
                /*Saturation Policies :- 4types
                AbortPolicy(RejectedExecutionException),
                DiscardPolicy(silently abort)
                DiscardOldestPolicy(removes old one in queue and add new task to queue)
                CallerRunsPolicy (remaining tasks will be executed in the thread which calls executor in our case main thread*/
        );
        System.out.println("executor is handled by"+Thread.currentThread().getName());


        // Submit 20 tasks to see the "CallerRunsPolicy" in action
        for (int i = 1; i <= 20; i++) {
            int id = i;
            executor.submit(() -> {
                System.out.println("Task " + id + " handled by " + Thread.currentThread().getName());
                try { Thread.sleep(2000); } catch (Exception e) {}
            });
        }
        executor.shutdown();
    }
}
