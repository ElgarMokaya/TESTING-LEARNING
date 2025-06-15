package com.example.JUNIT_TESTING.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    Calculator calculator;

    @BeforeEach
    void setUp(){
        calculator = new Calculator();
    }

    @Test
    void testAddition(){
      Integer answer=calculator.add(1,2);
      assertEquals(3,answer);
    }
    @Test
    void testMultiplication(){
        Integer answer=calculator.multiply(2,3);
        assertEquals(6,answer);
    }
    @Test
    void testDivision(){
        double answer=calculator.divide(2,3);
        assertEquals(0.6666666666666666,answer);
    }
    @Test
    void testSubtraction(){
        Integer answer=calculator.subtract(2,3);
        assertEquals(-1,answer);
    }

}
