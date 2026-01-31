package com.fulfilment.application.monolith.warehouses.service;

import com.fulfilment.application.monolith.location.LocationGateway;
import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.adapters.database.FulfillmentUnit;
import com.fulfilment.application.monolith.warehouses.adapters.database.FulfillmentUnitRepository;
import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class WarehouseService {

    @Inject
    WarehouseRepository warehouseRepository;
    @Inject
    FulfillmentUnitRepository fulfillmentUnitRepository;
    @Inject
    LocationGateway locationGateway;

    public List<Warehouse> listAll() {
        return warehouseRepository.getAll();
    }

    public com.fulfilment.application.monolith.warehouses.domain.models.Warehouse createWarehouse(
            com.fulfilment.application.monolith.warehouses.domain.models.Warehouse warehouse) {

        // 1. Business Unit Code Verification
        if (warehouseRepository.findByBusinessUnitCode(warehouse.businessUnitCode) != null) {
            throw new WebApplicationException(
                    "Warehouse with business unit code '" + warehouse.businessUnitCode + "' already exists.", 409);
        }

        // 2. Location Validation
        Location location = locationGateway.resolveByIdentifier(warehouse.location);
        if (location == null) {
            throw new WebApplicationException("Invalid location '" + warehouse.location + "'.", 422);
        }

        // 3. Warehouse Creation Feasibility
        long existingCount = warehouseRepository.count("location", warehouse.location);
        if (existingCount >= location.maxNumberOfWarehouses) {
            throw new WebApplicationException(
                    "Cannot create warehouse at location '" + warehouse.location + "'. Maximum number of warehouses reached.", 422);
        }

        // 4. Capacity and Stock Validation
        if (warehouse.capacity > location.maxCapacity) {
            throw new WebApplicationException(
                    "Capacity exceeds maximum allowed for location '" + warehouse.location + "'.", 422);
        }
        if (warehouse.stock > warehouse.capacity) {
            throw new WebApplicationException("Stock cannot exceed warehouse capacity.", 422);
        }

        warehouseRepository.create(warehouse);
        return warehouse;
    }

    public com.fulfilment.application.monolith.warehouses.domain.models.Warehouse replaceWarehouse(
            String businessUnitCode,
            com.fulfilment.application.monolith.warehouses.domain.models.Warehouse newWarehouse) {

        var oldWarehouse = warehouseRepository.findByBusinessUnitCode(businessUnitCode);
        if (oldWarehouse == null) {
            throw new WebApplicationException("Warehouse with business unit code '" + businessUnitCode + "' does not exist.", 404);
        }

        // 5. Capacity Accommodation
        if (newWarehouse.capacity < oldWarehouse.stock) {
            throw new WebApplicationException("New warehouse capacity cannot accommodate stock.", 422);
        }

        // 6. Stock Matching
        if (!Objects.equals(newWarehouse.stock, oldWarehouse.stock)) {
            throw new WebApplicationException("Stock of new warehouse must match stock of the warehouse being replaced.", 422);
        }

        warehouseRepository.update(newWarehouse);
        return newWarehouse;
    }

    public com.fulfilment.application.monolith.warehouses.domain.models.Warehouse findByBusinessUnitCode(String buCode) {
        return warehouseRepository.findByBusinessUnitCode(buCode);
    }

    public void archiveWarehouse(String buCode) {
        var warehouse = warehouseRepository.findByBusinessUnitCode(buCode);
        if (warehouse == null) {
            throw new WebApplicationException("Warehouse with business unit code '" + buCode + "' does not exist.", 404);
        }
        warehouse.archivedAt = LocalDateTime.now();
        warehouseRepository.update(warehouse);
    }


    public FulfillmentUnit assignWarehouseToProduct(Store store, Warehouse warehouse, Product product) {
        // 1. Business Unit Code Verification (already in createWarehouse)
        if (warehouseRepository.findByBusinessUnitCode(warehouse.businessUnitCode) == null) {
            throw new WebApplicationException("Warehouse with business unit code '"
                    + warehouse.businessUnitCode + "' does not exist.", 404);
        }

        // 2. Each Product can be fulfilled by max 2 Warehouses per Store
        long productAssignments = fulfillmentUnitRepository.count(
                "store = ?1 and product = ?2", store, product);
        if (productAssignments >= 2) {
            throw new WebApplicationException(
                    "Product '" + product.name + "' in store '" + store.name +
                            "' already has 2 warehouses assigned.", 422);
        }

        // 3. Each Store can be fulfilled by max 3 Warehouses
        long distinctWarehouses = fulfillmentUnitRepository.findDistinctWarehousesByStore(store).size();
        boolean warehouseAlreadyAssigned = fulfillmentUnitRepository.existsByStoreAndWarehouse(store, warehouse);
        if (distinctWarehouses >= 3 && !warehouseAlreadyAssigned) {
            throw new WebApplicationException(
                    "Store '" + store.name + "' already has 3 warehouses assigned.", 422);
        }

        // 4. Each Warehouse can store max 5 types of Products
        long distinctProducts = fulfillmentUnitRepository.findDistinctProductsByWarehouse(warehouse).size();
        boolean productAlreadyAssigned = fulfillmentUnitRepository.existsByWarehouseAndProduct(warehouse, product);
        if (distinctProducts >= 5 && !productAlreadyAssigned) {
            throw new WebApplicationException(
                    "Warehouse '" + warehouse.businessUnitCode +
                            "' already stores 5 product types.", 422);
        }

        // ✅ If all checks pass, persist the assignment
        FulfillmentUnit unit = new FulfillmentUnit();
        unit.setStore(store);
        unit.setWarehouse(warehouse);
        unit.setProduct(product);
        fulfillmentUnitRepository.persist(unit);

        return unit;
    }


}

