package com.springboot.security.service;

import com.springboot.security.dto.RoleDTO;
import com.springboot.security.entity.Role;
import com.springboot.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleDTO> findAll() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO findById(Long id) {
        return roleRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public RoleDTO create(RoleDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.getName())) {
            throw new RuntimeException("El nombre del rol ya existe");
        }

        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());

        return convertToDTO(roleRepository.save(role));
    }

    public RoleDTO update(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        if (!role.getName().equals(roleDTO.getName()) && 
            roleRepository.existsByName(roleDTO.getName())) {
            throw new RuntimeException("El nombre del rol ya existe");
        }

        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());

        return convertToDTO(roleRepository.save(role));
    }

    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        roleRepository.deleteById(id);
    }

    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        return dto;
    }
} 