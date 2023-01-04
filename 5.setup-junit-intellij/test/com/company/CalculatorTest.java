package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    //if we have a test method that does not assert any condition and does not throw any exceptions, even if it is absolutely empty, it will pass.
    @Test void integerDivision() {
        // so we can intentionally fail it with a fail assertion to avoid that kind of situation(to remind us there is a unit test we need to complete).
        fail("No implementation provided");
    }
}