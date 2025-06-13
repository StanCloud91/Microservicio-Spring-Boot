package com.springboot.security.service;

import com.springboot.security.dto.PermissionDTO;
import com.springboot.security.entity.Module;
import com.springboot.security.entity.Permission;
import com.springboot.security.entity.Role;
import com.springboot.security.repository.ModuleRepository;
import com.springboot.security.repository.PermissionRepository;
import com.springboot.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final ModuleRepository moduleRepository;

    public List<PermissionDTO> findAll() {
        return permissionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PermissionDTO> findByRoleId(Long roleId) {
        return permissionRepository.findByRoleId(roleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PermissionDTO findById(Long id) {
        return permissionRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
    }

    public PermissionDTO create(PermissionDTO permissionDTO) {
        if (permissionRepository.existsByName(permissionDTO.getName())) {
            throw new RuntimeException("El nombre del permiso ya existe");
        }

        Role role = roleRepository.findById(permissionDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Module module = moduleRepository.findById(permissionDTO.getModuleId())
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        Permission permission = new Permission();
        permission.setName(permissionDTO.getName());
        permission.setDescription(permissionDTO.getDescription());
        permission.setRole(role);
        permission.setModule(module);
        permission.setCanCreate(permissionDTO.isCanCreate());
        permission.setCanRead(permissionDTO.isCanRead());
        permission.setCanUpdate(permissionDTO.isCanUpdate());
        permission.setCanDelete(permissionDTO.isCanDelete());

        return convertToDTO(permissionRepository.save(permission));
    }

    public PermissionDTO update(Long id, PermissionDTO permissionDTO) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));

        if (!permission.getName().equals(permissionDTO.getName()) && 
            permissionRepository.existsByName(permissionDTO.getName())) {
            throw new RuntimeException("El nombre del permiso ya existe");
        }

        Role role = roleRepository.findById(permissionDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Module module = moduleRepository.findById(permissionDTO.getModuleId())
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        permission.setName(permissionDTO.getName());
        permission.setDescription(permissionDTO.getDescription());
        permission.setRole(role);
        permission.setModule(module);
        permission.setCanCreate(permissionDTO.isCanCreate());
        permission.setCanRead(permissionDTO.isCanRead());
        permission.setCanUpdate(permissionDTO.isCanUpdate());
        permission.setCanDelete(permissionDTO.isCanDelete());

        return convertToDTO(permissionRepository.save(permission));
    }

    public void delete(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new RuntimeException("Permiso no encontrado");
        }
        permissionRepository.deleteById(id);
    }

    private PermissionDTO convertToDTO(Permission permission) {
        PermissionDTO dto = new PermissionDTO();
        dto.setId(permission.getId());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());
        dto.setRoleId(permission.getRole().getId());
        dto.setModuleId(permission.getModule().getId());
        dto.setCanCreate(permission.isCanCreate());
        dto.setCanRead(permission.isCanRead());
        dto.setCanUpdate(permission.isCanUpdate());
        dto.setCanDelete(permission.isCanDelete());
        return dto;
    }
} 