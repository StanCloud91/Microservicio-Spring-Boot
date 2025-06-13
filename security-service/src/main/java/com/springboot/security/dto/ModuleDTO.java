package com.springboot.security.dto;

import lombok.Data;

@Data
public class ModuleDTO {
    private Long id;
    private String name;
    private String description;
    private String path;
    private boolean active;
} 