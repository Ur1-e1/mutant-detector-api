package com.magneto.mutant_detector.validation;

import com.magneto.mutant_detector.validation.ValidDnaSequence;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidDnaSequenceValidatorTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void closeValidator() {
        factory.close();
    }

    static class TestBean {
        @ValidDnaSequence
        String[] dna;

        TestBean(String[] dna) {
            this.dna = dna;
        }
    }

    @Test
    public void validDnaIsValid() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAA"};
        TestBean bean = new TestBean(dna);
        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertTrue(violations.isEmpty(), () -> "Expected no validation errors but got: " + violations);
    }

    @Test
    public void nullDnaIsInvalid() {
        TestBean bean = new TestBean(null);
        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void tooSmallMatrixIsInvalid() {
        String[] dna = {"ATG", "CAG", "TTA"};
        TestBean bean = new TestBean(dna);
        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void tooLargeMatrixIsInvalid() {
        int n = 1001;
        String row = "A".repeat(n);
        String[] dna = new String[n];
        for (int i = 0; i < n; i++) dna[i] = row;
        TestBean bean = new TestBean(dna);
        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertFalse(violations.isEmpty(), "Expected violations for too large matrix");
    }

    @Test
    public void nullRowIsInvalid() {
        String[] dna = {"ATGC", null, "TTAT", "AGAA"};
        TestBean bean = new TestBean(dna);
        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertFalse(violations.isEmpty());
        boolean hasNullRowMessage = violations.stream().anyMatch(v -> v.getMessage().contains("no debe contener filas nulas"));
        assertTrue(hasNullRowMessage, "Expected a violation message about null rows");
    }

    @Test
    public void nonSquareRowLengthIsInvalid() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AG"};
        TestBean bean = new TestBean(dna);
        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertFalse(violations.isEmpty());
        boolean hasSquareMessage = violations.stream().anyMatch(v -> v.getMessage().contains("matriz cuadrada"));
        assertTrue(hasSquareMessage, "Expected a violation message about square matrix");
    }

    @Test
    public void invalidCharactersAreInvalid() {
        String[] dna = {"ATGX", "CAGT", "TTAT", "AGAA"};
        TestBean bean = new TestBean(dna);
        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertFalse(violations.isEmpty());
        boolean hasCharMessage = violations.stream().anyMatch(v -> v.getMessage().contains("solo debe contener caracteres"));
        assertTrue(hasCharMessage, "Expected a violation message about invalid characters");
    }

    @Test
    public void multipleErrorsProduceMultipleViolations() {
        String[] dna = {"ATGX", null, "T", "AG"};
        TestBean bean = new TestBean(dna);
        Set<ConstraintViolation<TestBean>> violations = validator.validate(bean);
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() >= 2, "Expected multiple violations for multiple errors but got: " + violations.size());
    }
}

