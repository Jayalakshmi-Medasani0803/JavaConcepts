package com.java.concept.multithreading.virtualthreads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualVsPlatformThread {
    public static void main(String[] args) throws InterruptedException {
        int taskCount = 10000;

        // --- TEST 1: PLATFORM THREADS ---
        System.out.println("Starting Platform Threads...");
        runTest(Executors.newFixedThreadPool(200), taskCount, "Platform Pool (200)");

        // --- TEST 2: VIRTUAL THREADS ---
        System.out.println("\nStarting Virtual Threads...");
        runTest(Executors.newVirtualThreadPerTaskExecutor(), taskCount, "Virtual Threads");
    }

    private static void runTest(ExecutorService executor, int taskCount, String type) {
        Instant start = Instant.now();

        try (executor) {
            IntStream.range(0, taskCount).forEach(i -> {
                executor.submit(() -> {
                    try {
                        // Simulate I/O (Waiting)
                        Thread.sleep(Duration.ofSeconds(1));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            });
        } // Executor close() waits for all tasks to finish

        Instant finish = Instant.now();
        System.out.printf("[%s] Time taken: %d ms%n", type, Duration.between(start, finish).toMillis());
    }
}
