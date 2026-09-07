package com.iwms.module.product.mapper;

import com.iwms.module.product.dto.ProductRequest;
import com.iwms.module.product.dto.ProductResponse;
import com.iwms.module.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {

        return Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .unit(request.getUnit())
                .price(request.getPrice())
                .active(request.getActive())
                .build();
    }

    public ProductResponse toResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .unit(product.getUnit())
                .price(product.getPrice())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public void updateEntity(Product product, ProductRequest request) {

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setUnit(request.getUnit());
        product.setPrice(request.getPrice());
        product.setActive(request.getActive());
    }
}