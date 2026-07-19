package com.example.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IocConfigApp {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        MessagePrinter printer = context.getBean(MessagePrinter.class);
        printer.print();

        Counter counterA = context.getBean("singletonCounter", Counter.class);
        Counter counterB = context.getBean("singletonCounter", Counter.class);
        counterA.increment();
        counterA.increment();
        System.out.println("Singleton - counterA count: " + counterA.getCount());
        System.out.println("Singleton - counterB count: " + counterB.getCount());
        System.out.println("Singleton - same instance? " + (counterA == counterB));

        Counter protoA = context.getBean("prototypeCounter", Counter.class);
        Counter protoB = context.getBean("prototypeCounter", Counter.class);
        protoA.increment();
        protoA.increment();
        protoA.increment();
        System.out.println("Prototype - protoA count: " + protoA.getCount());
        System.out.println("Prototype - protoB count: " + protoB.getCount());
        System.out.println("Prototype - same instance? " + (protoA == protoB));

        context.close();
    }
}