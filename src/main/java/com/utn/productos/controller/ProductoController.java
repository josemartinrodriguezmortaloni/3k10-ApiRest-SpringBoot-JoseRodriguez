package com.utn.productos.controller;
import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Productos", description = "api enpints para ecommerce")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService service;

    @Autowired
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodosProductos(){
        List<ProductoResponseDTO> productos = service.obtenerTodosProductos();
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Obtener productos por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProductoPorId(@PathVariable Long id){
        ProductoResponseDTO producto = service.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Obtener productos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductoPorCategoria(@PathVariable Categoria categoria){
        List<ProductoResponseDTO> productos = service.obtenerProductoPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Agregar Productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error al crear producto")
    })
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informacion nuevo producto",
    required = true) @Valid @RequestBody ProductoDTO productoDTO){
        ProductoResponseDTO producto = service.crearProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @Operation(summary = "Actualizar producto completo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del producto actualizado", required = true)
            @Valid @RequestBody ProductoDTO productoDTO){
        ProductoResponseDTO producto = service.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Actualizar stock del producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Valor del stock invalido")
    })
    @PutMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @Parameter(description = "ID del producto a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Stock nuevo", required = true)
            @Valid @RequestBody ActualizarStockDTO actualizarStockDTO){
        ProductoResponseDTO producto = service.actualizarStock(id, actualizarStockDTO);
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Eliminar producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "No se puede eliminar: el producto tiene stock disponible")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarProducto(@PathVariable Long id){
        service.eliminarPoducto(id);
        return ResponseEntity.noContent().build();
    }
}
