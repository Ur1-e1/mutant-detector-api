package com.magneto.mutant_detector.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con estadisticas de ADN analizado")
public record StatsResponse(
        @Schema(description = "Cantidad de ADN detectado como mutante", example = "40")
        long countMutantDna,
        @Schema(description = "Cantidad de ADN detectado como humano", example = "100")
        long countHumanDna,
        @Schema(description = "Ratio entre mutantes y humanos", example = "0.4")
        double ratio
) {
}
