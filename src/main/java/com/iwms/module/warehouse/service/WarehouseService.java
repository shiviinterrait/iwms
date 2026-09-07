package com.iwms.module.warehouse.service;

import com.iwms.module.warehouse.dto.WarehouseRequest;
import com.iwms.module.warehouse.dto.WarehouseResponse;
import com.iwms.module.warehouse.entity.Warehouse;
import com.iwms.module.warehouse.mapper.WarehouseMapper;
import com.iwms.module.warehouse.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    public WarehouseService(
            WarehouseRepository warehouseRepository,
            WarehouseMapper warehouseMapper) {

        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    // ==========================================
    // CREATE WAREHOUSE
    // ==========================================

    public WarehouseResponse createWarehouse(
            WarehouseRequest request) {

        // Check duplicate warehouse code
        if (warehouseRepository.existsByCode(request.getCode())) {

            throw new RuntimeException(
                    "Warehouse code already exists: "
                            + request.getCode()
            );
        }

        // DTO → Entity
        Warehouse warehouse =
                warehouseMapper.toEntity(request);

        // Save to database
        Warehouse savedWarehouse =
                warehouseRepository.save(warehouse);

        // Entity → Response DTO
        return warehouseMapper.toResponse(savedWarehouse);
    }

    // ==========================================
    // GET ALL WAREHOUSES
    // ==========================================

    public List<WarehouseResponse> getAllWarehouses() {

        return warehouseRepository.findAll()
                .stream()
                .map(warehouseMapper::toResponse)
                .toList();
    }

    // ==========================================
    // GET WAREHOUSE BY ID
    // ==========================================

    public WarehouseResponse getWarehouseById(UUID id) {

        Warehouse warehouse =
                warehouseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Warehouse not found with id: "
                                                + id
                                )
                        );

        return warehouseMapper.toResponse(warehouse);
    }

    // ==========================================
    // UPDATE WAREHOUSE
    // ==========================================

    public WarehouseResponse updateWarehouse(
            UUID id,
            WarehouseRequest request) {

        Warehouse warehouse =
                warehouseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Warehouse not found with id: "
                                                + id
                                )
                        );

        // Check whether another warehouse
        // already uses this code
        if (!warehouse.getCode().equals(request.getCode())
                && warehouseRepository.existsByCode(request.getCode())) {

            throw new RuntimeException(
                    "Warehouse code already exists: "
                            + request.getCode()
            );
        }

        // Update existing entity
        warehouseMapper.updateEntity(
                warehouse,
                request
        );

        // Save updated entity
        Warehouse updatedWarehouse =
                warehouseRepository.save(warehouse);

        // Entity → Response DTO
        return warehouseMapper.toResponse(
                updatedWarehouse
        );
    }

    // ==========================================
    // DELETE WAREHOUSE
    // ==========================================

    public void deleteWarehouse(UUID id) {

        Warehouse warehouse =
                warehouseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Warehouse not found with id: "
                                                + id
                                )
                        );

        warehouseRepository.delete(warehouse);
    }
}