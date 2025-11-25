package com.magneto.mutant_detector.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
public @interface ValidDnaSequence {
    String message() default "El ADN debe ser una matriz cuadrada NxN y cada fila debe tener una longitud igual al tamaño de la matriz.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

