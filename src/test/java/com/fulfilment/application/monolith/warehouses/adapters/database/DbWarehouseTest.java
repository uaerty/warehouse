package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DbWarehouseTest {

    @Inject
    EntityManager em;

    @Test
    @Transactional
    void shouldPersistAndRetrieveDbWarehouse() {
        em.createQuery("DELETE FROM DbWarehouse").executeUpdate();
        DbWarehouse db = new DbWarehouse();
        db.businessUnitCode = "BU-001";
        db.location = "LOC-001";
        db.capacity = 100;
        db.stock = 50;
        db.createdAt = LocalDateTime.now();

        // Persist entity using EntityManager
        em.persist(db);
        em.flush();

        assertNotNull(db.id);

        // Retrieve by ID
        DbWarehouse found = em.find(DbWarehouse.class, db.id);
        assertNotNull(found);
        assertEquals("BU-001", found.businessUnitCode);
        assertEquals("LOC-001", found.location);
        assertEquals(100, found.capacity);
        assertEquals(50, found.stock);
    }

    @Test
    void shouldConvertEntityToDomain() {
        DbWarehouse db = new DbWarehouse();
        db.businessUnitCode = "BU-002";
        db.location = "LOC-002";
        db.capacity = 200;
        db.stock = 80;
        db.createdAt = LocalDateTime.now();

        Warehouse warehouse = db.toWarehouse();

        assertEquals("BU-002", warehouse.businessUnitCode);
        assertEquals("LOC-002", warehouse.location);
        assertEquals(200, warehouse.capacity);
        assertEquals(80, warehouse.stock);
    }

    @Test
    void shouldConvertDomainToEntity() {
        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU-003";
        warehouse.location = "LOC-003";
        warehouse.capacity = 300;
        warehouse.stock = 120;
        warehouse.createdAt = LocalDateTime.now();

        DbWarehouse db = DbWarehouse.fromWarehouse(warehouse);

        assertEquals("BU-003", db.businessUnitCode);
        assertEquals("LOC-003", db.location);
        assertEquals(300, db.capacity);
        assertEquals(120, db.stock);
    }

    @Test
    void shouldUpdateEntityFromDomain() {
        DbWarehouse db = new DbWarehouse();
        db.businessUnitCode = "BU-004";
        db.location = "LOC-004";
        db.capacity = 150;
        db.stock = 70;

        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU-UPDATED";
        warehouse.location = "LOC-UPDATED";
        warehouse.capacity = 400;
        warehouse.stock = 200;

        db.updateFromDomain(warehouse);

        assertEquals("BU-UPDATED", db.businessUnitCode);
        assertEquals("LOC-UPDATED", db.location);
        assertEquals(400, db.capacity);
        assertEquals(200, db.stock);
    }
}
