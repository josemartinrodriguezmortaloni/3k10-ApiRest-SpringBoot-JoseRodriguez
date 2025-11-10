package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema
public record ProductoDTO(
        @NotNull(message = "El nombre no puede ser nulo")
        @NotEmpty(message = "El nombre no puede estar vacio")
        @Size(min = 3, max = 100, message = "El nombre debe contener entre 3 y 100 caracteres")
        String nombre,

        @Size(max = 500, message = "La descripcion debe contener como máximo 500 caracteres")
        String descripcion,

        @NotNull(message = "El precio no puede ser nulo")
        @DecimalMin(value = "0.01", message = "El precio minimo es 0.01")
        Double precio,

        @NotNull(message = "El stock no puede ser nulo")
        @Min(value = 0, message = "El stock minimo es 0")
        Integer stock,

        @NotNull(message = "La categoria no puede ser nula")
        Categoria categoria
) {

    public Producto toEntity() {
        Producto producto = new Producto();
        updateEntity(producto);
        return producto;
    }

    public void updateEntity(Producto producto) {
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);
    }
}
