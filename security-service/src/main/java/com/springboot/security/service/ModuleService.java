package com.springboot.security.service;

import com.springboot.security.dto.ModuleDTO;
import com.springboot.security.entity.Module;
import com.springboot.security.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ModuleService {

    private final ModuleRepository moduleRepository;

    /**
     * Obtiene todos los módulos del sistema y los convierte a DTOs
     * 
     * Este método:
     * 1. Llama a moduleRepository.findAll() para obtener todos los módulos de la base de datos
     * 2. Convierte el resultado a un Stream para procesamiento funcional
     * 3. Mapea cada entidad Module a ModuleDTO usando el método convertToDTO
     * 4. Colecta los resultados en una nueva List<ModuleDTO>
     *
     * @return Lista de ModuleDTO con todos los módulos del sistema
     */
    public List<ModuleDTO> findAll() {
        return moduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ModuleDTO findById(Long id) {
        return moduleRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));
    }

    public ModuleDTO create(ModuleDTO moduleDTO) {
        if (moduleRepository.existsByName(moduleDTO.getName())) {
            throw new RuntimeException("El nombre del módulo ya existe");
        }

        Module module = new Module();
        module.setName(moduleDTO.getName());
        module.setDescription(moduleDTO.getDescription());
        module.setPath(moduleDTO.getPath());
        module.setActive(moduleDTO.isActive());

        return convertToDTO(moduleRepository.save(module));
    }

    public ModuleDTO update(Long id, ModuleDTO moduleDTO) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        if (!module.getName().equals(moduleDTO.getName()) && 
            moduleRepository.existsByName(moduleDTO.getName())) {
            throw new RuntimeException("El nombre del módulo ya existe");
        }

        module.setName(moduleDTO.getName());
        module.setDescription(moduleDTO.getDescription());
        module.setPath(moduleDTO.getPath());
        module.setActive(moduleDTO.isActive());

        return convertToDTO(moduleRepository.save(module));
    }

    public void delete(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new RuntimeException("Módulo no encontrado");
        }
        moduleRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Module a un DTO ModuleDTO
     * 
     * Este método:
     * 1. Crea un nuevo objeto ModuleDTO
     * 2. Asigna los valores de la entidad Module al DTO*/
    
    private ModuleDTO convertToDTO(Module module) {
        ModuleDTO dto = new ModuleDTO();
        dto.setId(module.getId());
        dto.setName(module.getName());
        dto.setDescription(module.getDescription());
        dto.setPath(module.getPath());
        dto.setActive(module.isActive());
        return dto;
    }
} 