package com.fulfilment.application.monolith.warehouses;

import com.fulfilment.application.monolith.location.LocationGateway;
import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.adapters.database.FulfillmentUnitRepository;
import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.service.WarehouseService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class WarehouseServiceTest {

    @Inject
    WarehouseService service;

    @Inject
    WarehouseRepository warehouseRepository;

    @Inject
    FulfillmentUnitRepository fulfillmentUnitRepository;

    @Inject
    LocationGateway locationGateway;


    @Test
    void shouldFailWhenWarehouseAlreadyExists() {
        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU-001";
        assertThrows(IllegalArgumentException.class, () -> service.createWarehouse(warehouse));
    }

    @Test
    void shouldFailWhenCapacityTooHigh() {
        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU-002";
        warehouse.location = "ZWOLLE-001";
        warehouse.capacity = 200;
        warehouse.stock = 10;

        assertThrows(IllegalArgumentException.class, () -> service.createWarehouse(warehouse));
    }


    @Test
    void shouldFailReplaceWhenCapacityTooLow() {
        Warehouse oldWarehouse = new Warehouse();
        oldWarehouse.businessUnitCode = "BU-004";
        oldWarehouse.stock = 30;

        Warehouse newWarehouse = new Warehouse();
        newWarehouse.businessUnitCode = "BU-004";
        newWarehouse.stock = 30;
        newWarehouse.capacity = 20;


        assertThrows(IllegalArgumentException.class, () -> service.replaceWarehouse("BU-004", newWarehouse));
    }


    @Test
    void shouldFailAssignWhenProductAlreadyHasTwoWarehouses() {
        Store store = new Store();
        store.name = "Main Store";

        Warehouse warehouse = new Warehouse();
        warehouse.businessUnitCode = "BU-007";

        Product product = new Product("Phone");

        assertThrows(IllegalArgumentException.class, () -> service.assignWarehouseToProduct(store, warehouse, product));
    }
}
