package com.java.concept.multithreading.semaphore;

import java.util.concurrent.Semaphore;

public class CoffeeShop {

    // A semaphore with 3 permits (3 seats available)
    private static final Semaphore seats = new Semaphore(3);

    public static void main(String[] args) {
        // Creating 6 customers
        for (int i = 1; i <= 6; i++) {
            new Customer(i).start();
        }
    }

    static class Customer extends Thread {
        private int id;

        Customer(int id) {
            this.id = id;
        }

        public void run() {
            try {
                System.out.println("Customer " + id + " is waiting for a seat...");

                // Acquire a permit (sit down)
                seats.acquire();

                System.out.println("Customer " + id + " has SEATED and is drinking coffee.");

                // Simulate time spent drinking coffee
                Thread.sleep((long) (Math.random() * 30000));

                System.out.println("Customer " + id + " is LEAVING.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Release the permit (free up the seat)
                seats.release();
            }
        }
    }
}