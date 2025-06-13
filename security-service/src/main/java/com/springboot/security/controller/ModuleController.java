package com.springboot.security.controller;

import com.springboot.security.dto.ModuleDTO;
import com.springboot.security.service.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Tag(name = "Módulos", description = "API para la gestión de módulos")
public class ModuleController {

    private final ModuleService moduleService;

    @Operation(summary = "Obtener todos los módulos")
    @GetMapping
    @PreAuthorize("hasAuthority('READ_MODULES')")
    public ResponseEntity<List<ModuleDTO>> getAllModules() {
        return ResponseEntity.ok(moduleService.findAll());
    }

    @Operation(summary = "Obtener módulo por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_MODULES')")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.findById(id));
    }

    @Operation(summary = "Crear nuevo módulo")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_MODULES')")
    public ResponseEntity<ModuleDTO> createModule(@RequestBody ModuleDTO moduleDTO) {
        return ResponseEntity.ok(moduleService.create(moduleDTO));
    }

    @Operation(summary = "Actualizar módulo")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_MODULES')")
    public ResponseEntity<ModuleDTO> updateModule(@PathVariable Long id, @RequestBody ModuleDTO moduleDTO) {
        return ResponseEntity.ok(moduleService.update(id, moduleDTO));
    }

    @Operation(summary = "Eliminar módulo")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_MODULES')")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.delete(id);
        return ResponseEntity.ok().build();
    }
} 