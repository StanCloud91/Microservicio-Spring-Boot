package com.springboot.security.dto;

import lombok.Data;

@Data
public class PermissionDTO {
    private Long id;
    private String name;
    private String description;
    private Long moduleId;
    private Long roleId;
    private boolean canCreate;
    private boolean canRead;
    private boolean canUpdate;
    private boolean canDelete;
} 