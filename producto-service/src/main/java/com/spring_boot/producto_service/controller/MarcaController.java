package com.spring_boot.producto_service.controller;

import com.spring_boot.producto_service.entity.Marca;
import com.spring_boot.producto_service.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
@Tag(name = "Marcas", description = "API para gestión de marcas de productos")
public class MarcaController {
    private final MarcaService marcaService;

    @Operation(summary = "Listar todas las marcas")
    @GetMapping
    public ResponseEntity<List<Marca>> getAll() {
        return ResponseEntity.ok(marcaService.findAll());
    }

    @Operation(summary = "Obtener marca por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Marca> getById(@PathVariable Long id) {
        return marcaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear marca")
    @PostMapping
    public ResponseEntity<Marca> create(@RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.save(marca));
    }

    @Operation(summary = "Actualizar marca")
    @PutMapping("/{id}")
    public ResponseEntity<Marca> update(@PathVariable Long id, @RequestBody Marca marca) {
        return marcaService.findById(id)
                .map(m -> {
                    marca.setId(id);
                    return ResponseEntity.ok(marcaService.save(marca));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar marca")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 