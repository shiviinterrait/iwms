package com.iwms.module.stock.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {

    @NotNull(message = "Warehouse ID is required")
    private UUID warehouseId;

    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantity cannot be negative")
    private BigDecimal quantity;

    @NotNull(message = "Minimum stock is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum stock cannot be negative")
    private BigDecimal minimumStock;

    @DecimalMin(value = "0.0", inclusive = true, message = "Maximum stock cannot be negative")
    private BigDecimal maximumStock;
}