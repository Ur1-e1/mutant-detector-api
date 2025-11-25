package com.magneto.mutant_detector.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {
    private static final int MAX_SIZE = 1000;

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null) return false;
        int n = dna.length;
        List<String> errors = new ArrayList<>();

        if (n < 4 || n > MAX_SIZE) {
            errors.add("El ADN debe ser al menos 4x4 y no mayor a 1000x1000.");
        }

        for (String row : dna) {
            if (row == null) {
                errors.add("El ADN no debe contener filas nulas.");
                continue;
            }
            if (row.length() != n) {
                errors.add("El ADN debe ser una matriz cuadrada NxN y cada fila debe tener una longitud igual al tamaño de la matriz.");
            }
            if (!row.matches("^[ATCGatcg]+$")) {
                errors.add("El ADN solo debe contener caracteres A, T, C, G.");
            }
        }

        if (!errors.isEmpty()) {
            context.disableDefaultConstraintViolation();
            for (String error : errors) {
                context.buildConstraintViolationWithTemplate(error).addConstraintViolation();
            }
            return false;
        }

        return true;
    }
}

