package com.spring_boot.orden_compra_service.service;

import com.spring_boot.orden_compra_service.entity.OrdenCompra;
import com.spring_boot.orden_compra_service.entity.OrdenCompraDetalle;
import com.spring_boot.orden_compra_service.repository.OrdenCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenCompraService {
    private final OrdenCompraRepository ordenCompraRepository;

    public List<OrdenCompra> findAll() {
        return ordenCompraRepository.findAll();
    }

    public Optional<OrdenCompra> findById(Long id) {
        return ordenCompraRepository.findById(id);
    }

    @Transactional
    public OrdenCompra save(OrdenCompra ordenCompra) {
        // Calcular total
        double total = 0.0;
        if (ordenCompra.getDetalles() != null) {
            for (OrdenCompraDetalle detalle : ordenCompra.getDetalles()) {
                detalle.setOrdenCompra(ordenCompra);
                detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
                total += detalle.getSubtotal();
            }
        }
        ordenCompra.setTotal(total);
        if (ordenCompra.getFecha() == null) {
            ordenCompra.setFecha(LocalDateTime.now());
        }
        if (ordenCompra.getEstado() == null) {
            ordenCompra.setEstado("PENDIENTE");
        }
        return ordenCompraRepository.save(ordenCompra);
    }

    public void deleteById(Long id) {
        ordenCompraRepository.deleteById(id);
    }
} 