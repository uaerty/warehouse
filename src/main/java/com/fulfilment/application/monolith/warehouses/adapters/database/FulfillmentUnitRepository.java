package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;

@ApplicationScoped
public class FulfillmentUnitRepository implements PanacheRepository<FulfillmentUnit> {

    /**
     * Count how many fulfillment units exist for a given store and product.
     */
    public long countByStoreAndProduct(Store store, Product product) {
        return count("store = ?1 and product = ?2", store, product);
    }

    /**
     * Check if a warehouse is already assigned to a store.
     */
    public boolean existsByStoreAndWarehouse(Store store, Warehouse warehouse) {
        return count("store = ?1 and warehouse = ?2", store, warehouse) > 0;
    }

    /**
     * Check if a product is already assigned to a warehouse.
     */
    public boolean existsByWarehouseAndProduct(Warehouse warehouse, Product product) {
        return count("warehouse = ?1 and product = ?2", warehouse, product) > 0;
    }

    /**
     * Find distinct warehouses assigned to a store.
     */
    public List<Warehouse> findDistinctWarehousesByStore(Store store) {
        return getEntityManager()
                .createQuery("select distinct f.warehouse from FulfillmentUnit f where f.store = :store", Warehouse.class)
                .setParameter("store", store)
                .getResultList();
    }

    /**
     * Find distinct products assigned to a warehouse.
     */
    public List<Product> findDistinctProductsByWarehouse(Warehouse warehouse) {
        return getEntityManager()
                .createQuery("select distinct f.product from FulfillmentUnit f where f.warehouse = :warehouse", Product.class)
                .setParameter("warehouse", warehouse)
                .getResultList();
    }
}

