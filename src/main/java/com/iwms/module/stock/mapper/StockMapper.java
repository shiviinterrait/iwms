package com.iwms.module.stock.mapper;

import com.iwms.module.stock.dto.StockResponse;
import com.iwms.module.stock.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public StockResponse toResponse(Stock stock) {

        return StockResponse.builder()
                .id(stock.getId())

                .warehouseId(stock.getWarehouse().getId())
                .warehouseName(stock.getWarehouse().getName())

                .productId(stock.getProduct().getId())
                .productSku(stock.getProduct().getSku())
                .productName(stock.getProduct().getName())

                .quantity(stock.getQuantity())
                .minimumStock(stock.getMinimumStock())
                .maximumStock(stock.getMaximumStock())

                .lastUpdated(stock.getLastUpdated())
                .build();
    }
}