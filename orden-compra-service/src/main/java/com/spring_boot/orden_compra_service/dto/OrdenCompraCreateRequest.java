package com.spring_boot.orden_compra_service.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrdenCompraCreateRequest {
    private LocalDateTime fecha;
    private Long clienteId;
    private String nombreCliente;
    private String emailCliente;
    private String estado;
    private List<OrdenCompraDetalleCreateRequest> detalles;
} 