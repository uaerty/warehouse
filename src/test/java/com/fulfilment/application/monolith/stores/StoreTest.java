package com.fulfilment.application.monolith.stores;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StoreTest {

    @Test
    void shouldCreateStoreWithNameOnly() {
        Store store = new Store("TestStore");

        assertEquals("TestStore", store.name);
        assertEquals(0, store.quantityProductsInStock);
        assertNull(store.id); // not persisted yet
    }

    @Test
    void shouldSetAndGetFields() {
        Store store = new Store();
        store.name = "WarehouseStore";
        store.quantityProductsInStock = 100;

        assertEquals("WarehouseStore", store.name);
        assertEquals(100, store.quantityProductsInStock);
    }

    @Test
    @Transactional
    void shouldPersistAndFindStore() {
        Store store = new Store("PersistedStore");
        store.quantityProductsInStock = 50;

        // Persist using PanacheEntity's persist()
        store.persist();

        assertNotNull(store.id);

        // Find by ID
        Store found = Store.findById(store.id);

        assertNotNull(found);
        assertEquals("PersistedStore", found.name);
        assertEquals(50, found.quantityProductsInStock);
    }

    @Test
    @Transactional
    void shouldFindByNameUsingPanache() {
        Store store = new Store("UniqueStore");
        store.quantityProductsInStock = 10;
        store.persist();

        Store found = Store.find("name", "UniqueStore").firstResult();

        assertNotNull(found);
        assertEquals("UniqueStore", found.name);
        assertEquals(10, found.quantityProductsInStock);
    }
}
