package com.spring_boot.producto_service.controller;

import com.spring_boot.producto_service.entity.Color;
import com.spring_boot.producto_service.service.ColorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colores")
@RequiredArgsConstructor
@Tag(name = "Colores", description = "API para gestión de colores de productos")
public class ColorController {
    private final ColorService colorService;

    @Operation(summary = "Listar todos los colores")
    @GetMapping
    public ResponseEntity<List<Color>> getAll() {
        return ResponseEntity.ok(colorService.findAll());
    }

    @Operation(summary = "Obtener color por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Color> getById(@PathVariable Long id) {
        return colorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear color")
    @PostMapping
    public ResponseEntity<Color> create(@RequestBody Color color) {
        return ResponseEntity.ok(colorService.save(color));
    }

    @Operation(summary = "Actualizar color")
    @PutMapping("/{id}")
    public ResponseEntity<Color> update(@PathVariable Long id, @RequestBody Color color) {
        return colorService.findById(id)
                .map(c -> {
                    color.setId(id);
                    return ResponseEntity.ok(colorService.save(color));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar color")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        colorService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 