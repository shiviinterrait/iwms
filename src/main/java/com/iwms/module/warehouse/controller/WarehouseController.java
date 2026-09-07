package com.iwms.module.warehouse.controller;

import com.iwms.module.warehouse.dto.WarehouseRequest;
import com.iwms.module.warehouse.dto.WarehouseResponse;
import com.iwms.module.warehouse.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(
            @Valid @RequestBody WarehouseRequest request) {

        WarehouseResponse response =
                warehouseService.createWarehouse(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses() {

        return ResponseEntity.ok(
                warehouseService.getAllWarehouses()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                warehouseService.getWarehouseById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(
            @PathVariable UUID id,
            @Valid @RequestBody WarehouseRequest request) {

        return ResponseEntity.ok(
                warehouseService.updateWarehouse(id, request)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(
            @PathVariable UUID id) {

        warehouseService.deleteWarehouse(id);

        return ResponseEntity.noContent().build();
    }
}