package com.java.concept.multithreading.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureEg {
    public static void main(String[] args) throws InterruptedException {
        // 1. Setup your Custom Executor (from our previous talk!)
        ExecutorService customPool = Executors.newFixedThreadPool(4, r -> new Thread(r, "HomeWorker"));

        System.out.println("[Main] Starting Smart Home Sequence...");

        // 2. supplyAsync: Start the first task
        CompletableFuture<String> settingsFuture = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1000);
            System.out.println("[" + Thread.currentThread().getName() + "] Fetched Settings: Coffee=Espresso, Temp=38C");
            return "Espresso";
        });

        // 3. thenCompose: Chain dependent tasks (Coffee depends on Settings)
        // Use compose when the next method also returns a CompletableFuture
        CompletableFuture<String> coffeeFuture = settingsFuture.thenCompose(type ->
                CompletableFuture.supplyAsync(() -> {
                    simulateDelay(2000);
                    return type + " is Ready!";
                }, customPool)
        );

        // 4. supplyAsync (Independent): Start heating the shower at the same time
        CompletableFuture<String> showerFuture = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1500);
            return "Shower is Warm!";
        }, customPool);

        // 5. thenCombine: Wait for BOTH (Coffee AND Shower) to finish
        CompletableFuture<String> morningReady = coffeeFuture.thenCombine(showerFuture
                , (coffee, shower) -> coffee + " & " + shower + " -> Time to wake up!");

        // 6. thenAccept: The final "Sink" (Consumer)
        morningReady.thenAccept(finalStatus -> {
            System.out.println("[Main Notification] " + finalStatus);
        });

        // 7. exceptionally: Error handling for the whole chain
        morningReady.exceptionally(ex -> {
            System.err.println("Home System Error: " + ex.getMessage());
            return null;
        });

        // Keep main alive for a bit to see the results (since CF is non-blocking)
        Thread.sleep(5000);
        customPool.shutdown();
    }

    private static void simulateDelay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}
