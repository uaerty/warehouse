package com.fulfilment.application.monolith.warehouses.adapters.mapper;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.service.WarehouseService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class WarehouseServiceTest {

    @Inject
    WarehouseService service;

    private Warehouse buildWarehouse(String buCode, String location, int capacity, int stock) {
        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = buCode;
        warehouse.location = location;
        warehouse.capacity = capacity;
        warehouse.stock = stock;
        warehouse.createdAt = LocalDateTime.now();
        return warehouse;
    }

    @Test
    void shouldThrowConflictWhenWarehouseAlreadyExists() {
        Warehouse warehouse = buildWarehouse("BU-011", "AMSTERDAM-001", 100, 50);

        // First creation should succeed
        service.createWarehouse(warehouse);

        // Second creation with same businessUnitCode should throw WebApplicationException (409 Conflict)
        WebApplicationException ex = assertThrows(WebApplicationException.class,
                () -> service.createWarehouse(warehouse));

        assertEquals(409, ex.getResponse().getStatus());
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void shouldThrowUnprocessableEntityForInvalidLocation() {
        Warehouse warehouse = buildWarehouse("BU-002", "INVALID_LOC", 100, 50);

        WebApplicationException ex = assertThrows(WebApplicationException.class,
                () -> service.createWarehouse(warehouse));

        assertEquals(409, ex.getResponse().getStatus());
    }

    @Test
    void shouldThrowNotFoundWhenReplacingNonExistingWarehouse() {
        Warehouse newWarehouse = buildWarehouse("BU-999", "LOC-999", 100, 50);

        WebApplicationException ex = assertThrows(WebApplicationException.class,
                () -> service.replaceWarehouse("BU-999", newWarehouse));

        assertEquals(404, ex.getResponse().getStatus());
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void shouldThrowUnprocessableEntityWhenStockExceedsCapacity() {
        Warehouse warehouse = buildWarehouse("BU-003", "LOC-003", 100, 200);

        WebApplicationException ex = assertThrows(WebApplicationException.class,
                () -> service.createWarehouse(warehouse));

        assertEquals(409, ex.getResponse().getStatus());
    }
}
