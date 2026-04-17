package com.java.concept.multithreading.threadpools.executor;

public class JobRunnable implements Runnable{
    JobRequestQueue jobRequestQueue = new JobRequestQueue();

    @Override
    public void run() {
        try {
            while (true) {
                String request = jobRequestQueue.getRequestsQueue().take();
                String threadName = Thread.currentThread().getName();
                System.out.println("   [Worker " + threadName + "] Processing: " + request);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Worker shut down.");
        }
    }
}
