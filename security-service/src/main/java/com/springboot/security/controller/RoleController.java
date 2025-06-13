package com.springboot.security.controller;

import com.springboot.security.dto.RoleDTO;
import com.springboot.security.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "API para la gestión de roles")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Obtener todos los roles")
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @Operation(summary = "Obtener rol por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @Operation(summary = "Crear nuevo rol")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLES')")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.create(roleDTO));
    }

    @Operation(summary = "Actualizar rol")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLES')")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.update(id, roleDTO));
    }

    @Operation(summary = "Eliminar rol")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLES')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }
} 