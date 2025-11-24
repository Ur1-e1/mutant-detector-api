package com.magneto.mutant_detector.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import io.swagger.v3.oas.annotations.media.Schema;

public record DnaRequest(
        @Schema(description = "Array de strings que representa la matriz NxN del ADN", example = "[\"ATGC\", \"CAGT\", \"TTAT\", \"AGAA\"]")
        @NotNull(message = "El ADN no debe ser nulo")
        @NotEmpty(message = "El ADN no debe estar vacio")
        @Size(min = 4, message = "El ADN debe ser al menos 4x4")
        @ValidNxNMatrix
        String @Pattern(regexp = "^[ATCGatcg]+$", message = "El ADN solo debe contener caracteres A, T, C, G") [] dna
) {

    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = NxNValidator.class)
    public @interface ValidNxNMatrix {
        String message() default "El ADN debe ser una matriz cuadrada NxN y cada fila debe tener una longitud igual al tamaño de la matriz.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class NxNValidator implements ConstraintValidator<ValidNxNMatrix, String[]> {
        @Override
        public boolean isValid(String[] dna, ConstraintValidatorContext context) {
            if (dna == null) return false;
            int n = dna.length;
            if (n < 4) return false;
            for (String row : dna) {
                if (row == null) return false;
                if (row.length() != n) return false;

                if (!row.matches("^[ATCGatcg]+$")) return false;
            }
            return true;
        }
    }
}
