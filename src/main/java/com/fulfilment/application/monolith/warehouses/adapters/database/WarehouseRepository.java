package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class WarehouseRepository implements WarehouseStore, PanacheRepository<DbWarehouse> {

    @Override
    public List<Warehouse> getAll() {
        return this.listAll().stream().map(DbWarehouse::toWarehouse).toList();
    }

    @Override
    public void create(Warehouse warehouse) {
        DbWarehouse entity = DbWarehouse.fromWarehouse(warehouse);
        this.persist(entity);
    }

    @Override
    public void update(Warehouse warehouse) {
        DbWarehouse entity = this.find("businessUnitCode", warehouse.businessUnitCode).firstResult();
        if (entity == null) {
            throw new IllegalArgumentException("Warehouse with id " + warehouse.businessUnitCode + " not found");
        }
        entity.updateFromDomain(warehouse);
        this.persist(entity);
    }

    @Override
    public void remove(Warehouse warehouse) {
        DbWarehouse entity = this.find("businessUnitCode", warehouse.businessUnitCode).firstResult();
        if (entity != null) {
            this.delete(entity);
        }
    }

    @Override
    public Warehouse findByBusinessUnitCode(String buCode) {
        DbWarehouse entity = this.find("businessUnitCode", buCode).firstResult();
        return entity != null ? entity.toWarehouse() : null;
    }

}

