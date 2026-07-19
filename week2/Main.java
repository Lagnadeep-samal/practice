package com.pattern.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testing Singleton Pattern in Single Thread ===");
        
        // Retrieve instances of Logger
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Log messages
        logger1.log("This is the first log message.");
        logger2.log("This is the second log message.");

        // Check if both references point to the same instance
        if (logger1 == logger2) {
            System.out.println("Verification Success: Both references point to the exact same Logger instance.");
            System.out.println("Instance 1 HashCode: " + logger1.hashCode());
            System.out.println("Instance 2 HashCode: " + logger2.hashCode());
        } else {
            System.out.println("Verification Failure: Different Logger instances exist!");
        }

        System.out.println("\n=== Testing Singleton Pattern in Multi-threaded Environment ===");
        
        // Creating a thread pool to test concurrent access to getInstance()
        int threadCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        
        // Array to store the logger references from different threads
        Logger[] loggers = new Logger[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(() -> {
                loggers[index] = Logger.getInstance();
                loggers[index].log("Log message from Thread " + Thread.currentThread().getName());
            });
        }

        // Shut down executor and wait for tasks to complete
        executor.shutdown();
        try {
            if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("\nAll threads completed. Verifying references across all threads...");
                
                boolean allSame = true;
                Logger primaryInstance = loggers[0];
                System.out.println("Thread 0 Logger HashCode: " + (primaryInstance != null ? primaryInstance.hashCode() : "null"));
                
                for (int i = 1; i < threadCount; i++) {
                    System.out.println("Thread " + i + " Logger HashCode: " + (loggers[i] != null ? loggers[i].hashCode() : "null"));
                    if (loggers[i] != primaryInstance) {
                        allSame = false;
                    }
                }

                if (allSame && primaryInstance != null) {
                    System.out.println("Verification Success: All threads shared the single Logger instance. Thread safety verified.");
                } else {
                    System.out.println("Verification Failure: Thread safety failed or some instances were null.");
                }
            } else {
                System.out.println("Test timed out before threads finished execution.");
            }
        } catch (InterruptedException e) {
            System.err.println("Thread verification interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
