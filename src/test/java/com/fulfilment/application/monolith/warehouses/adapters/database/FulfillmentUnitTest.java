package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class FulfillmentUnitTest {

    @Test
    void shouldSetAndGetRelationships() {
        Store store = new Store("TestStore");
        store.quantityProductsInStock = 10;

        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU-001";
        warehouse.location = "LOC-001";
        warehouse.capacity = 100;
        warehouse.stock = 50;

        Product product = new Product();
        product.name = "TestProduct";
        product.price = BigDecimal.valueOf(99.99);

        FulfillmentUnit fu = new FulfillmentUnit();
        fu.setStore(store);
        fu.setWarehouse(warehouse);
        fu.setProduct(product);

        assertEquals("TestStore", fu.getStore().name);
        assertEquals("BU-001", fu.getWarehouse().businessUnitCode);
        assertEquals("TestProduct", fu.getProduct().name);
    }
}
