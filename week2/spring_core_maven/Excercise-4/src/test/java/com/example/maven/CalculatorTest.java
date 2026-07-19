package com.example.maven;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void addsTwoNumbers() {
        Calculator calculator = new Calculator();
        assertEquals(11, calculator.add(4, 7));
    }

    @Test
    void subtractsTwoNumbers() {
        Calculator calculator = new Calculator();
        assertEquals(3, calculator.subtract(7, 4));
    }
}