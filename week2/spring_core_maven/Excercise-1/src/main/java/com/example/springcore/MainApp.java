package com.example.springcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        GreetingService greetingService =
                (GreetingService) context.getBean("greetingService");

        greetingService.greet();

        System.out.println("Injected message value: " + greetingService.getMessage());
    }
}