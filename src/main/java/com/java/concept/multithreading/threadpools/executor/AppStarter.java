package com.java.concept.multithreading.threadpools.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppStarter {
    public static void main(String[] args) {
        JobRequestQueue jobRequestQueue = new JobRequestQueue();
        for (int i = 1; i <= 40; i++) {
            String request = "Request-" + i;
            System.out.println("[Server] Received: " + request);
            jobRequestQueue.addProjectUpdateRequests(request);
        }

        ExecutorService executorService = new ThreadPoolExecutor(2, 4, 1,
                TimeUnit.MINUTES, new LinkedBlockingQueue<>(5));
        try {
            for (int i = 0; i < 4; i++) {
                executorService.execute(new JobRunnable());
            }
        } finally {
            executorService.shutdown();
        }
    }
}
