package com.handson.springresthandson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Step 3: Hello World RESTful Web Service.
 * <p>
 * The most basic REST endpoint - proves the embedded server and
 * Spring MVC dispatcher are wired up correctly.
 * <p>
 * Try: GET http://localhost:8080/hello
 *      GET http://localhost:8080/hello?name=World
 */
@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }
}
