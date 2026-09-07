package com.iwms.module.warehouse.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {

    private UUID id;
    private String code;
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private Boolean active;
    private LocalDateTime createdAt;
}