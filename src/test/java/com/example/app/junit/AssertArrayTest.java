package com.example.app.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AssertArrayTest {

    @Test
    public void assertArrayTest() {
        int[] array1 = { 1, 2, 3 };
        int[] array2 = { 1, 2, 3 };

        assertArrayEquals(array1, array2);
    }
}
