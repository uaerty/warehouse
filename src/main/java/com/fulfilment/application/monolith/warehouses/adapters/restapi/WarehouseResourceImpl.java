package com.fulfilment.application.monolith.warehouses.adapters.restapi;
import com.fulfilment.application.monolith.products.Product;
import com.fulfilment.application.monolith.stores.Store;
import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.adapters.mapper.FulfillmentUnitMapper;
import com.fulfilment.application.monolith.warehouses.adapters.mapper.WarehouseMapper;
import com.fulfilment.application.monolith.warehouses.service.WarehouseService;
import com.warehouse.api.WarehouseResource;
import com.warehouse.api.beans.FulfillmentUnit;
import com.warehouse.api.beans.FullfillCriteria;
import com.warehouse.api.beans.Warehouse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;

@RequestScoped
public class WarehouseResourceImpl implements WarehouseResource {

    @Inject
    private WarehouseRepository warehouseRepository;
    @Inject
    private WarehouseService warehouseService;

    @Override
    public List<Warehouse> listAllWarehousesUnits() {
        return warehouseRepository.getAll().stream().map(this::toWarehouseResponse).toList();
    }

    @Override
    public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
        var domainWarehouse = WarehouseMapper.toDomain(data);
        var createdDomainWarehouse = warehouseService.createWarehouse(domainWarehouse);
        return WarehouseMapper.toBean(createdDomainWarehouse);
    }

    @Override
    public Warehouse getAWarehouseUnitByID(String id) {
        var domainWarehouse = warehouseService.findByBusinessUnitCode(id);
        return WarehouseMapper.toBean(domainWarehouse);
    }

    @Override
    public void archiveAWarehouseUnitByID(String id) {
        warehouseService.archiveWarehouse(id);
    }

    @Override
    public Warehouse replaceTheCurrentActiveWarehouse(
            String businessUnitCode, @NotNull Warehouse data) {
        var domainWarehouse = WarehouseMapper.toDomain(data);
        var replacedDomainWarehouse = warehouseService.replaceWarehouse(businessUnitCode, domainWarehouse);
        return WarehouseMapper.toBean(replacedDomainWarehouse);
    }

    @Override
    public FulfillmentUnit associateWarehousesOfCertainProductsToDeterminedStores(FullfillCriteria data) {
        Store store = Store.findById(data.getStoreId());
        var domainWarehouse = warehouseService.findByBusinessUnitCode(data.getWarehouseBusinessUnitCode());
        Product product = Product.findById(data.getProductId());
        if (store == null || domainWarehouse == null || product == null) {
            throw new WebApplicationException("Invalid store/warehouse/product identifier", 404);
        }
        var fullfilledWarehouse = warehouseService.assignWarehouseToProduct(store, domainWarehouse, product);
        return FulfillmentUnitMapper.toBean(fullfilledWarehouse);
    }


    private Warehouse toWarehouseResponse(
            com.fulfilment.application.monolith.warehouses.domain.models.Warehouse warehouse) {
        var response = new Warehouse();
        response.setBusinessUnitCode(warehouse.businessUnitCode);
        response.setLocation(warehouse.location);
        response.setCapacity(warehouse.capacity);
        response.setStock(warehouse.stock);

        return response;
    }
}
