package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class WarehouseRepositoryTest {

    @Inject
    WarehouseRepository repository;

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
    @Transactional
    void shouldCreateAndFindWarehouse() {
        repository.deleteAll();
        Warehouse warehouse = buildWarehouse("BU-001", "LOC-001", 100, 50);

        repository.create(warehouse);

        Warehouse found = repository.findByBusinessUnitCode("BU-001");
        assertNotNull(found);
        assertEquals("BU-001", found.businessUnitCode);
        assertEquals("LOC-001", found.location);
        assertEquals(100, found.capacity);
        assertEquals(50, found.stock);
    }

    @Test
    @Transactional
    void shouldReturnAllWarehouses() {
        Warehouse w1 = buildWarehouse("BU-002", "LOC-002", 200, 80);
        Warehouse w2 = buildWarehouse("BU-003", "LOC-003", 300, 120);

        repository.create(w1);
        repository.create(w2);

        List<Warehouse> all = repository.getAll();
        assertTrue(all.stream().anyMatch(w -> "BU-002".equals(w.businessUnitCode)));
        assertTrue(all.stream().anyMatch(w -> "BU-003".equals(w.businessUnitCode)));
    }

    @Test
    @Transactional
    void shouldUpdateWarehouse() {
        Warehouse warehouse = buildWarehouse("BU-004", "LOC-004", 150, 70);
        repository.create(warehouse);

        Warehouse updated = buildWarehouse("BU-004", "LOC-UPDATED", 400, 200);
        repository.update(updated);

        Warehouse found = repository.findByBusinessUnitCode("BU-004");
        assertEquals("LOC-UPDATED", found.location);
        assertEquals(400, found.capacity);
        assertEquals(200, found.stock);
    }

    @Test
    @Transactional
    void shouldRemoveWarehouse() {
        Warehouse warehouse = buildWarehouse("BU-005", "LOC-005", 500, 250);
        repository.create(warehouse);

        repository.remove(warehouse);

        Warehouse found = repository.findByBusinessUnitCode("BU-005");
        assertNull(found, "Warehouse should be removed and return null");
    }


    @Test
    @Transactional
    void shouldThrowNullWhenWarehouseNotFound() {
        Warehouse found = repository.findByBusinessUnitCode("NON-EXISTENT");
        assertNull(found, "Warehouse should be removed and return null");
    }
}

