package com.example.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExercise {

    private static final Logger logger = LoggerFactory.getLogger(LoggingExercise.class);

    public static void main(String[] args) {

        logger.trace("This is a TRACE message - very fine-grained debug info");
        logger.debug("This is a DEBUG message - useful for diagnosing issues");
        logger.info("This is an INFO message - general application progress");

        logger.warn("Disk usage is at 85% - approaching capacity limit");
        logger.warn("Configuration value 'timeout' not set, using default of {} ms", 5000);

        logger.error("Failed to connect to database at {}", "jdbc:mysql://localhost:3306/mydb");

        try {
            processOrder(-1);
        } catch (IllegalArgumentException e) {
            logger.error("Order processing failed for order id: {}", -1, e);
        }

        simulateFileProcessing("missing-report.csv");

        logger.info("Logging exercise completed. Check console output and logs/application.log");
    }

    private static void processOrder(int orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order id must be positive, got: " + orderId);
        }
        logger.info("Processing order {}", orderId);
    }

    private static void simulateFileProcessing(String fileName) {
        logger.warn("File '{}' does not follow the expected naming convention", fileName);

        boolean fileExists = false;
        if (!fileExists) {
            logger.error("File '{}' could not be found. Aborting processing.", fileName);
            return;
        }

        logger.info("File '{}' processed successfully", fileName);
    }
}