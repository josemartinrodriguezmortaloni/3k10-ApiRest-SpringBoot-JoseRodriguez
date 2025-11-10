package com.utn.productos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la aplicación.
 * @ControllerAdvice permite interceptar excepciones lanzadas por los controllers
 * y proporcionar respuestas consistentes y personalizadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción cuando no se encuentra un producto.
     * Retorna un error 404 NOT FOUND.
     *
     * @param ex Excepción lanzada
     * @param request Información de la petición HTTP
     * @return ResponseEntity con el error estructurado
     */
    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarProductoNoEncontrado(
            ProductoNotFoundException ex,
            WebRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Producto no encontrado",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    /**
     * Maneja errores de validación de Bean Validation.
     * Se activa cuando un DTO con @Valid falla las validaciones.
     * Retorna un error 400 BAD REQUEST con detalles de cada campo inválido.
     *
     * @param ex Excepción que contiene los errores de validación
     * @param request Información de la petición HTTP
     * @return ResponseEntity con los errores de validación detallados
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarErroresValidacion(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        // Construir un mapa con todos los errores de validación
        Map<String, String> erroresCampos = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensajeError = error.getDefaultMessage();
            erroresCampos.put(nombreCampo, mensajeError);
        });

        // Construir respuesta estructurada
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", java.time.LocalDateTime.now());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de validación");
        respuesta.put("mensaje", "Los datos proporcionados no son válidos");
        respuesta.put("errores", erroresCampos);
        respuesta.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja cualquier otra excepción no contemplada específicamente.
     * Retorna un error 500 INTERNAL SERVER ERROR.
     * Actúa como red de seguridad para evitar exponer detalles internos.
     *
     * @param ex Excepción lanzada
     * @param request Información de la petición HTTP
     * @return ResponseEntity con el error genérico
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionGeneral(
            Exception ex,
            WebRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                "Ocurrió un error inesperado. Por favor, contacte al administrador.",
                request.getDescription(false).replace("uri=", "")
        );

        // Log del error real para debugging (en producción usar un logger apropiado)
        System.err.println("Error no manejado: " + ex.getMessage());
        ex.printStackTrace();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}