package com.magneto.mutant_detector.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DnaRequestTest {

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

    @Test
    public void validDnaIsValid() {
        String[] dna = {"ATGC","CAGT","TTAT","AGAA"};
        DnaRequest req = new DnaRequest(dna);
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty(), () -> "Expected no validation errors but got: " + violations);
    }

    @Test
    public void nullDnaIsInvalid() {
        DnaRequest req = new DnaRequest(null);
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nonSquareIsInvalid() {
        String[] dna = {"ATG","CAG","TTA"};
        DnaRequest req = new DnaRequest(dna);
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void invalidCharsIsInvalid() {
        String[] dna = {"ATGX","CAGT","TTAT","AGAA"};
        DnaRequest req = new DnaRequest(dna);
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }
}
