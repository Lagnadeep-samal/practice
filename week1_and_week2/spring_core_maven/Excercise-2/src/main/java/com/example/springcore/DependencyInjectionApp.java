package com.example.springcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyInjectionApp {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        Car car = (Car) context.getBean("car");
        car.drive();

        Engine engine = (Engine) context.getBean("engine");
        System.out.println("Standalone engine bean: " + engine.describe());
    }
}