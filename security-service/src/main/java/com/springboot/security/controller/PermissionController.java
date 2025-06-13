package com.springboot.security.controller;

import com.springboot.security.dto.PermissionDTO;
import com.springboot.security.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Tag(name = "Permisos", description = "API para la gestión de permisos")
public class PermissionController {

    private final PermissionService permissionService;

    @Operation(summary = "Obtener todos los permisos")
    @GetMapping
    @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    @Operation(summary = "Obtener permisos por rol")
    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
    public ResponseEntity<List<PermissionDTO>> getPermissionsByRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(permissionService.findByRoleId(roleId));
    }

    @Operation(summary = "Obtener permiso por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
    public ResponseEntity<PermissionDTO> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.findById(id));
    }

    @Operation(summary = "Crear nuevo permiso")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PERMISSIONS')")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
        return ResponseEntity.ok(permissionService.create(permissionDTO));
    }

    @Operation(summary = "Actualizar permiso")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PERMISSIONS')")
    public ResponseEntity<PermissionDTO> updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        return ResponseEntity.ok(permissionService.update(id, permissionDTO));
    }

    @Operation(summary = "Eliminar permiso")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PERMISSIONS')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.delete(id);
        return ResponseEntity.ok().build();
    }
} 