package com.spring_boot.orden_compra_service.controller;

import com.spring_boot.orden_compra_service.entity.OrdenCompra;
import com.spring_boot.orden_compra_service.service.OrdenCompraService;
import com.spring_boot.orden_compra_service.dto.OrdenCompraCreateRequest;
import com.spring_boot.orden_compra_service.dto.OrdenCompraDetalleCreateRequest;
import com.spring_boot.orden_compra_service.entity.OrdenCompraDetalle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-compra")
@RequiredArgsConstructor
@Tag(name = "Órdenes de Compra", description = "API para gestión de órdenes de compra/facturas retail")
public class OrdenCompraController {
    private final OrdenCompraService ordenCompraService;

    @Operation(summary = "Listar todas las órdenes de compra")
    @GetMapping
    public ResponseEntity<List<OrdenCompra>> getAll() {
        return ResponseEntity.ok(ordenCompraService.findAll());
    }

    @Operation(summary = "Obtener orden de compra por ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> getById(@PathVariable Long id) {
        return ordenCompraService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear orden de compra")
    @PostMapping
    public ResponseEntity<OrdenCompra> create(@RequestBody OrdenCompraCreateRequest request) {
        OrdenCompra ordenCompra = mapToEntity(request);
        return ResponseEntity.ok(ordenCompraService.save(ordenCompra));
    }

    @Operation(summary = "Actualizar orden de compra")
    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompra> update(@PathVariable Long id, @RequestBody OrdenCompra ordenCompra) {
        return ordenCompraService.findById(id)
                .map(o -> {
                    ordenCompra.setId(id);
                    return ResponseEntity.ok(ordenCompraService.save(ordenCompra));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar orden de compra")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ordenCompraService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private OrdenCompra mapToEntity(OrdenCompraCreateRequest request) {
        OrdenCompra orden = new OrdenCompra();
        orden.setFecha(request.getFecha());
        orden.setClienteId(request.getClienteId());
        orden.setNombreCliente(request.getNombreCliente());
        orden.setEmailCliente(request.getEmailCliente());
        orden.setEstado(request.getEstado());
        if (request.getDetalles() != null) {
            List<OrdenCompraDetalle> detalles = request.getDetalles().stream().map(det -> {
                OrdenCompraDetalle detalle = new OrdenCompraDetalle();
                detalle.setProductoId(det.getProductoId());
                detalle.setNombreProducto(det.getNombreProducto());
                detalle.setMarcaProducto(det.getMarcaProducto());
                detalle.setColorProducto(det.getColorProducto());
                detalle.setCantidad(det.getCantidad());
                detalle.setPrecioUnitario(det.getPrecioUnitario());
                return detalle;
            }).toList();
            orden.setDetalles(detalles);
        }
        return orden;
    }
} 