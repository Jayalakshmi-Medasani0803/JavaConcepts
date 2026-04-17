package com.java.concept.multithreading.threadpools.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JobRequestQueue {
    private static final BlockingQueue<String> requestsQueue = new LinkedBlockingQueue<>();

    public void addProjectUpdateRequests(String request) {
        requestsQueue.add(request);
    }
    public BlockingQueue<String> getRequestsQueue() {
        return requestsQueue;
    }

}
