package com.iwms.module.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequest {

    @NotBlank(message = "Supplier code is required")
    private String code;

    @NotBlank(message = "Supplier name is required")
    private String name;

    private String contactPerson;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private Boolean active;
}