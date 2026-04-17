package com.java.concept.multithreading.threadpools;

public class Main {
    public static void main(String[] args) {
       AtomicLockRace atomicLockRace = new AtomicLockRace();
        try {
            atomicLockRace.runTest();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}