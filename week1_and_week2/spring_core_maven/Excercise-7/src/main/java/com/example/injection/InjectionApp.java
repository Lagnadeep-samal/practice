package com.example.injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectionApp {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        EmployeeConstructorDI viaConstructor =
                (EmployeeConstructorDI) context.getBean("employeeConstructorDI");
        viaConstructor.printDetails();

        EmployeeSetterDI viaSetter =
                (EmployeeSetterDI) context.getBean("employeeSetterDI");
        viaSetter.printDetails();

        Address address = (Address) context.getBean("address");
        System.out.println("Shared address bean: " + address);
    }
}