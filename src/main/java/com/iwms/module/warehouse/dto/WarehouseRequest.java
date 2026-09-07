package com.iwms.module.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequest {

    @NotBlank(message = "Warehouse code is required")
    private String code;

    @NotBlank(message = "Warehouse name is required")
    private String name;

    private String address;
    private String city;
    private String state;
    private String pincode;

    private Boolean active;
}