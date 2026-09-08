package com.iwms.module.stock.service;

import com.iwms.module.product.entity.Product;
import com.iwms.module.product.repository.ProductRepository;
import com.iwms.module.stock.dto.StockRequest;
import com.iwms.module.stock.dto.StockResponse;
import com.iwms.module.stock.entity.Stock;
import com.iwms.module.stock.mapper.StockMapper;
import com.iwms.module.stock.repository.StockRepository;
import com.iwms.module.warehouse.entity.Warehouse;
import com.iwms.module.warehouse.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockMapper stockMapper;

    public StockService(
            StockRepository stockRepository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository,
            StockMapper stockMapper) {

        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.stockMapper = stockMapper;
    }

    @Transactional
    public StockResponse createStock(StockRequest request) {

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Warehouse not found with ID: "
                                        + request.getWarehouseId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with ID: "
                                        + request.getProductId()));

        if (stockRepository.existsByWarehouseAndProduct(warehouse, product)) {
            throw new RuntimeException(
                    "Stock already exists for this warehouse and product");
        }

        Stock stock = Stock.builder()
                .warehouse(warehouse)
                .product(product)
                .quantity(request.getQuantity())
                .minimumStock(request.getMinimumStock())
                .maximumStock(request.getMaximumStock())
                .build();

        Stock savedStock = stockRepository.save(stock);

        return stockMapper.toResponse(savedStock);
    }

    @Transactional(readOnly = true)
    public StockResponse getStockById(UUID id) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Stock not found with ID: " + id));

        return stockMapper.toResponse(stock);
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getAllStock() {

        return stockRepository.findAll()
                .stream()
                .map(stockMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getStockByWarehouse(UUID warehouseId) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Warehouse not found with ID: "
                                        + warehouseId));

        return stockRepository.findByWarehouse(warehouse)
                .stream()
                .map(stockMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getStockByProduct(UUID productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with ID: "
                                        + productId));

        return stockRepository.findByProduct(product)
                .stream()
                .map(stockMapper::toResponse)
                .toList();
    }

    @Transactional
    public StockResponse updateStock(
            UUID id,
            StockRequest request) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Stock not found with ID: " + id));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Warehouse not found with ID: "
                                        + request.getWarehouseId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with ID: "
                                        + request.getProductId()));

        stockRepository.findByWarehouseAndProduct(warehouse, product)
                .ifPresent(existingStock -> {
                    if (!existingStock.getId().equals(id)) {
                        throw new RuntimeException(
                                "Stock already exists for this warehouse and product");
                    }
                });

        stock.setWarehouse(warehouse);
        stock.setProduct(product);
        stock.setQuantity(request.getQuantity());
        stock.setMinimumStock(request.getMinimumStock());
        stock.setMaximumStock(request.getMaximumStock());

        Stock updatedStock = stockRepository.save(stock);

        return stockMapper.toResponse(updatedStock);
    }

    @Transactional
    public void deleteStock(UUID id) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Stock not found with ID: " + id));

        stockRepository.delete(stock);
    }
}