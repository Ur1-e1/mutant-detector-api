package com.magneto.mutant_detector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidDnaExceptionTest {

    @Test
    void testExceptionMessageAndType() {
        InvalidDnaException ex = new InvalidDnaException("mensaje prueba");
        assertEquals("mensaje prueba", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }
}

