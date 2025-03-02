package com.reservaki.reservaki.application.service;

import com.reservaki.reservaki.domain.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        // Cria um mock de MethodArgumentNotValidException
        MethodArgumentNotValidException mockException = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        // Configura o mock com um erro de campo
        FieldError fieldError = new FieldError("objectName", "fieldName", "Error message");
        when(mockException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        // Executa o método de tratamento
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(mockException);

        // Verifica as asserções
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("fieldName"));
        assertEquals("Error message", response.getBody().get("fieldName"));
    }

    @Test
    void shouldHandleGenericException() {
        // Cria uma exceção genérica
        Exception genericException = new Exception("Unexpected error");

        // Executa o método de tratamento
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleAllExceptions(genericException);

        // Verifica as asserções
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("message"));
        assertEquals("An unexpected error occurred", response.getBody().get("message"));
    }

    @Test
    void shouldHandleMultipleFieldErrors() {
        // Cria um mock de MethodArgumentNotValidException
        MethodArgumentNotValidException mockException = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        // Configura o mock com múltiplos erros de campo
        FieldError fieldError1 = new FieldError("objectName", "field1", "Error 1");
        FieldError fieldError2 = new FieldError("objectName", "field2", "Error 2");
        when(mockException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        // Executa o método de tratamento
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(mockException);

        // Verifica as asserções
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Error 1", response.getBody().get("field1"));
        assertEquals("Error 2", response.getBody().get("field2"));
    }
}