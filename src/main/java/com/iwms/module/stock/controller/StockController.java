package com.iwms.module.stock.controller;

import com.iwms.module.stock.dto.StockRequest;
import com.iwms.module.stock.dto.StockResponse;
import com.iwms.module.stock.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse createStock(
            @Valid @RequestBody StockRequest request) {

        return stockService.createStock(request);
    }

    @GetMapping
    public List<StockResponse> getAllStock() {

        return stockService.getAllStock();
    }

    @GetMapping("/{id}")
    public StockResponse getStockById(
            @PathVariable UUID id) {

        return stockService.getStockById(id);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public List<StockResponse> getStockByWarehouse(
            @PathVariable UUID warehouseId) {

        return stockService.getStockByWarehouse(warehouseId);
    }

    @GetMapping("/product/{productId}")
    public List<StockResponse> getStockByProduct(
            @PathVariable UUID productId) {

        return stockService.getStockByProduct(productId);
    }

    @PutMapping("/{id}")
    public StockResponse updateStock(
            @PathVariable UUID id,
            @Valid @RequestBody StockRequest request) {

        return stockService.updateStock(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStock(
            @PathVariable UUID id) {

        stockService.deleteStock(id);
    }
}