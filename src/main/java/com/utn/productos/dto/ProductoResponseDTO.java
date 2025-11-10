package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record ProductoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        Categoria categoria
) {
    public static ProductoResponseDTO fromEntity(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria()
        );
    }
}
