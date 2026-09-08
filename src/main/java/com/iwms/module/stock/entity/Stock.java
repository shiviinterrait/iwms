package com.iwms.module.stock.entity;

import com.iwms.module.product.entity.Product;
import com.iwms.module.warehouse.entity.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "stock",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_stock_warehouse_product",
                        columnNames = {"warehouse_id", "product_id"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantity;

    @Column(name = "minimum_stock", nullable = false, precision = 12, scale = 2)
    private BigDecimal minimumStock;

    @Column(name = "maximum_stock", precision = 12, scale = 2)
    private BigDecimal maximumStock;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @PrePersist
    protected void onCreate() {

        if (quantity == null) {
            quantity = BigDecimal.ZERO;
        }

        if (minimumStock == null) {
            minimumStock = BigDecimal.ZERO;
        }

        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}