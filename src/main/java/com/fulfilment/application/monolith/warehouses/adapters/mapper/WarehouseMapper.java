package com.fulfilment.application.monolith.warehouses.adapters.mapper;

import com.warehouse.api.beans.Warehouse;

public class WarehouseMapper {

    public static com.fulfilment.application.monolith.warehouses.domain.models.Warehouse toDomain(Warehouse bean) {
        var domain = new com.fulfilment.application.monolith.warehouses.domain.models.Warehouse();
        domain.businessUnitCode = bean.getBusinessUnitCode();
        domain.location = bean.getLocation();
        domain.capacity = bean.getCapacity();
        domain.stock = bean.getStock();
        return domain;
    }

    public static Warehouse toBean(com.fulfilment.application.monolith.warehouses.domain.models.Warehouse domain) {
        var bean = new Warehouse();
        bean.setBusinessUnitCode(domain.businessUnitCode);
        bean.setLocation(domain.location);
        bean.setCapacity(domain.capacity);
        bean.setStock(domain.stock);
        return bean;
    }
}

