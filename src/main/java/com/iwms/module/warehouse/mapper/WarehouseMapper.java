package com.iwms.module.warehouse.mapper;

import com.iwms.module.warehouse.dto.WarehouseRequest;
import com.iwms.module.warehouse.dto.WarehouseResponse;
import com.iwms.module.warehouse.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    // Request DTO → Entity
    public Warehouse toEntity(WarehouseRequest request) {

        return Warehouse.builder()
                .code(request.getCode())
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .active(request.getActive())
                .build();
    }

    // Entity → Response DTO
    public WarehouseResponse toResponse(Warehouse warehouse) {

        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .code(warehouse.getCode())
                .name(warehouse.getName())
                .address(warehouse.getAddress())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .pincode(warehouse.getPincode())
                .active(warehouse.getActive())
                .createdAt(warehouse.getCreatedAt())
                .build();
    }

    // Update existing Entity from Request DTO
    public void updateEntity(
            Warehouse warehouse,
            WarehouseRequest request) {

        warehouse.setCode(request.getCode());
        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());
        warehouse.setCity(request.getCity());
        warehouse.setState(request.getState());
        warehouse.setPincode(request.getPincode());
        warehouse.setActive(request.getActive());
    }
}