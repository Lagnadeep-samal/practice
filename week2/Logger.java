package com.pattern.singleton;

/**
 * Logger class implementing the Singleton pattern.
 * This ensures only one instance of Logger exists in the application.
 */
public class Logger {
    // Volatile keyword ensures that multiple threads handle the uniqueInstance variable correctly
    // when it is being initialized to the Logger instance.
    private static volatile Logger uniqueInstance;

    // Private constructor prevents instantiation from other classes
    private Logger() {
        System.out.println("Logger initialized.");
    }

    /**
     * Public method to get the single instance of the Logger class.
     * Implements Double-Checked Locking to ensure thread safety and high performance.
     *
     * @return the single instance of Logger
     */
    public static Logger getInstance() {
        if (uniqueInstance == null) {
            synchronized (Logger.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Logger();
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * Method to log message.
     *
     * @param message the message to log
     */
    public void log(String message) {
        System.out.println("[LOG]: " + message);
    }
}
