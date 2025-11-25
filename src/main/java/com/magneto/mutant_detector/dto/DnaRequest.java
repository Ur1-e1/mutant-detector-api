package com.magneto.mutant_detector.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import com.magneto.mutant_detector.validation.ValidDnaSequence;

public record DnaRequest(
        @Schema(description = "Array de strings que representa la matriz NxN del ADN", example = "[\"ATGC\", \"CAGT\", \"TTAT\", \"AGAA\"]")
        @NotNull(message = "El ADN no debe ser nulo")
        @NotEmpty(message = "El ADN no debe estar vacio")
        @Size(min = 4, message = "El ADN debe ser al menos 4x4")
        @ValidDnaSequence
        String[] dna
) {

}
