package com.utn.productos.service;

import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.exception.ProductoNotFoundException;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repo;

    @Autowired
    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ProductoResponseDTO crearProducto(ProductoDTO productoDTO) {
        Producto prod = productoDTO.toEntity();
        Producto saved = repo.save(prod);
        return ProductoResponseDTO.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerProductoPorId(Long id) {
        Producto prod = repo.findById(id).orElseThrow(() -> new ProductoNotFoundException("No se encuentra el producto con id:" + id));
        return ProductoResponseDTO.fromEntity(prod);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerTodosProductos() {
        return repo.findAll()
                .stream()
                .map(ProductoResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductoPorCategoria(Categoria categoria) {
        return repo.findByCategoria(categoria)
                .stream()
                .map(ProductoResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto prod = repo.findById(id).orElseThrow(() -> new ProductoNotFoundException("No se encuentra el producto con id:" + id));
        productoDTO.updateEntity(prod);
        Producto actualizado = repo.save(prod);
        return ProductoResponseDTO.fromEntity(actualizado);
    }

    @Transactional
    public ProductoResponseDTO actualizarStock(Long id, ActualizarStockDTO productoDTO) {
        Producto prod = repo.findById(id).orElseThrow(() -> new ProductoNotFoundException("No se encuentra el producto con id:" + id));
        prod.setStock(productoDTO.stock());
        Producto actualizado = repo.save(prod);
        return ProductoResponseDTO.fromEntity(actualizado);
    }

    public void eliminarPoducto(Long id) {
        repo.deleteById(id);
    }
}
