package com.iwms.module.stock.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private UUID id;

    private UUID warehouseId;
    private String warehouseName;

    private UUID productId;
    private String productSku;
    private String productName;

    private BigDecimal quantity;
    private BigDecimal minimumStock;
    private BigDecimal maximumStock;

    private LocalDateTime lastUpdated;
}