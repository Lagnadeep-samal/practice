package com.handson.springresthandson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Step 1: Create a Spring Web Project using Maven.
 * <p>
 * This is the entry point for the whole hands-on series. Running this class
 * starts an embedded Tomcat server and boots the Spring application context.
 * The @ImportResource annotation wires in the classic XML bean configuration
 * used in Step 2 (Spring Core - Load Country from Spring Configuration XML),
 * showing how XML-based config and annotation-based config can coexist.
 */
@SpringBootApplication
@ImportResource("classpath:spring-config.xml")
public class SpringRestHandsonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestHandsonApplication.class, args);
    }
}
