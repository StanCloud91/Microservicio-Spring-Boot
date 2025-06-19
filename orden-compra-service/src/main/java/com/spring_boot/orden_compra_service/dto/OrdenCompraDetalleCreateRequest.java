package com.spring_boot.orden_compra_service.dto;

import lombok.Data;

@Data
public class OrdenCompraDetalleCreateRequest {
    private Long productoId;
    private String nombreProducto;
    private String marcaProducto;
    private String colorProducto;
    private Integer cantidad;
    private Double precioUnitario;
} 