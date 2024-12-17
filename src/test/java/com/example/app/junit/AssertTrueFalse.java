package com.example.app.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AssertTrueFalse {

    @Test
    public void test1() {
        assertTrue(true);
        assertFalse(false);
    }

    @Test
    public void test2() {

        boolean first = 3 > 2;
        boolean second = 2 > 3;

        assertTrue(first);
        assertFalse(second);
    }

}
