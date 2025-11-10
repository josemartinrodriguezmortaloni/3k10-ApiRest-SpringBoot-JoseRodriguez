package com.utn.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema
public record ActualizarStockDTO(
        @NotNull(message = "El stock no puede ser nulo")
        @Min(value = 0, message = "El minimo es 0")
        Integer stock
) {
}
