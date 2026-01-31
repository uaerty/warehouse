package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import jakarta.persistence.*;

@Entity
@Table(name = "fulfillment_unit")
public class FulfillmentUnit {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Product product;

    // --- Getters & Setters ---
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
