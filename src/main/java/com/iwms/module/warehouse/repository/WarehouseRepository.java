package com.iwms.module.warehouse.repository;

import com.iwms.module.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    boolean existsByCode(String code);
}