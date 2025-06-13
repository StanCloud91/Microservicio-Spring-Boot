package com.springboot.security.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Long roleId;
    private boolean enabled;
} 