package com.magneto.mutant_detector.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleInvalidDna() {
        InvalidDnaException ex = new InvalidDnaException("bad dna");
        ResponseEntity<Map<String, Object>> resp = handler.handleInvalidDna(ex);
        assertEquals(400, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Invalid DNA", resp.getBody().get("error"));
        assertEquals("bad dna", resp.getBody().get("message"));
    }

    @Test
    void testHandleValidationCollectsFieldErrors() {
        BindingResult br = Mockito.mock(BindingResult.class);
        FieldError fe = new FieldError("target", "dna", "must not be empty");
        Mockito.when(br.getFieldErrors()).thenReturn(List.of(fe));
        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(ex.getBindingResult()).thenReturn(br);

        ResponseEntity<Map<String, Object>> resp = handler.handleValidation(ex);
        assertEquals(400, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Validation failed", resp.getBody().get("error"));
        assertTrue(((String) resp.getBody().get("message")).contains("dna: must not be empty"));
    }

    @Test
    void testHandleIllegalArgument() {
        IllegalArgumentException ex = new IllegalArgumentException("arg bad");
        ResponseEntity<Map<String, Object>> resp = handler.handleIllegalArg(ex);
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("Invalid Argument", resp.getBody().get("error"));
        assertEquals("arg bad", resp.getBody().get("message"));
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("boom");
        ResponseEntity<Map<String, Object>> resp = handler.handleGeneric(ex);
        assertEquals(500, resp.getStatusCodeValue());
        assertEquals("Internal Server Error", resp.getBody().get("error"));
        assertEquals("boom", resp.getBody().get("message"));
    }
}
