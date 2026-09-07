package com.iwms.module.supplier.service;

import com.iwms.module.supplier.dto.SupplierRequest;
import com.iwms.module.supplier.dto.SupplierResponse;
import com.iwms.module.supplier.entity.Supplier;
import com.iwms.module.supplier.mapper.SupplierMapper;
import com.iwms.module.supplier.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(
            SupplierRepository supplierRepository,
            SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    public SupplierResponse createSupplier(SupplierRequest request) {

        if (supplierRepository.existsByCode(request.getCode())) {
            throw new RuntimeException(
                    "Supplier code already exists: " + request.getCode());
        }

        Supplier supplier = supplierMapper.toEntity(request);

        Supplier savedSupplier = supplierRepository.save(supplier);

        return supplierMapper.toResponse(savedSupplier);
    }

    public List<SupplierResponse> getAllSuppliers() {

        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toResponse)
                .toList();
    }

    public SupplierResponse getSupplierById(UUID id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier not found with id: " + id));

        return supplierMapper.toResponse(supplier);
    }

    public SupplierResponse updateSupplier(
            UUID id,
            SupplierRequest request) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier not found with id: " + id));

        if (!supplier.getCode().equals(request.getCode())
                && supplierRepository.existsByCode(request.getCode())) {

            throw new RuntimeException(
                    "Supplier code already exists: " + request.getCode());
        }

        supplierMapper.updateEntity(supplier, request);

        Supplier updatedSupplier = supplierRepository.save(supplier);

        return supplierMapper.toResponse(updatedSupplier);
    }

    public void deleteSupplier(UUID id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier not found with id: " + id));

        supplierRepository.delete(supplier);
    }
}