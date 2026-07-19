package com.example.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

@Configuration
public class AppConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MessagePrinter messagePrinter() {
        MessagePrinter printer = new MessagePrinter();
        printer.setMessage("Configured entirely in Java, no XML needed!");
        return printer;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Counter singletonCounter() {
        return new Counter();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Counter prototypeCounter() {
        return new Counter();
    }
}