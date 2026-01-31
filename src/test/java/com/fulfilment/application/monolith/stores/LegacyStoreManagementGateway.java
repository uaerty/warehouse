package com.fulfilment.application.monolith.stores;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@QuarkusTest
class LegacyStoreManagerGatewayTest {

    @Inject
    LegacyStoreManagerGateway gateway;

    @Test
    void shouldCreateStoreOnLegacySystem() {
        Store store = new Store();
        store.name = "TestStore";
        store.quantityProductsInStock = 42;

        // Verify that no exception is thrown when creating
        assertDoesNotThrow(() -> gateway.createStoreOnLegacySystem(store));
    }

    @Test
    void shouldUpdateStoreOnLegacySystem() {
        Store store = new Store();
        store.name = "UpdatedStore";
        store.quantityProductsInStock = 100;

        // Verify that no exception is thrown when updating
        assertDoesNotThrow(() -> gateway.updateStoreOnLegacySystem(store));
    }
}
