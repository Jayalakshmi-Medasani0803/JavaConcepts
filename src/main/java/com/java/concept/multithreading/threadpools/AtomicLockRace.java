package com.java.concept.multithreading.threadpools;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicLockRace {
    private int unsafeCounter = 0;
    private final AtomicInteger atomicCounter = new AtomicInteger(0);
    private final ReentrantLock lock = new ReentrantLock();
    private int lockedCounter = 0;

    public void runTest() throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                unsafeCounter++; // Race condition here
                atomicCounter.incrementAndGet(); // Thread-safe (CAS)

                lock.lock();
                try { lockedCounter++; } finally { lock.unlock(); }
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Unsafe: " + unsafeCounter); // Likely < 2000
        System.out.println("Atomic: " + atomicCounter); // Always 2000
        System.out.println("Locked: " + lockedCounter); // Always 2000
    }
}
