package com.iwms.module.stock.repository;

import com.iwms.module.product.entity.Product;
import com.iwms.module.stock.entity.Stock;
import com.iwms.module.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {

    Optional<Stock> findByWarehouseAndProduct(
            Warehouse warehouse,
            Product product
    );

    List<Stock> findByWarehouse(Warehouse warehouse);

    List<Stock> findByProduct(Product product);

    boolean existsByWarehouseAndProduct(
            Warehouse warehouse,
            Product product
    );

    void deleteByWarehouseAndProduct(
            Warehouse warehouse,
            Product product
    );
}