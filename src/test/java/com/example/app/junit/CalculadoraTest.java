package com.example.app.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {

    calculadora calculadora = new calculadora();

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Se ejecuta antes de todos los test");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Se ejecuta después despues de los test");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Se ejecuta después de terminar cada test");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Se ejecuta antes de cada test");
    }

    @Test
    public void testSuma() {
        float resultado = calculadora.sumar(2, 3);

        assertEquals(resultado, 5);

    }

}
