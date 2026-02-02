package com.fulfilment.application.monolith.warehouses.adapters.mapper;

import com.fulfilment.application.monolith.warehouses.adapters.database.FulfillmentUnit;
import com.warehouse.api.beans.Product;
import com.warehouse.api.beans.Store;
import com.warehouse.api.beans.Warehouse;

public class FulfillmentUnitMapper {

    public static com.warehouse.api.beans.FulfillmentUnit toBean(FulfillmentUnit domain) {
        var bean = new com.warehouse.api.beans.FulfillmentUnit();

        var storeBean = new Store();
        storeBean.setId(domain.getStore().id);
        storeBean.setName(domain.getStore().name);
        storeBean.setQuantityProductsInStock(domain.getStore().quantityProductsInStock);

        var warehouseBean = new Warehouse();
        warehouseBean.setBusinessUnitCode(domain.getWarehouse().businessUnitCode);
        warehouseBean.setLocation(domain.getWarehouse().location);
        warehouseBean.setCapacity(domain.getWarehouse().capacity);
        warehouseBean.setStock(domain.getWarehouse().stock);

        var productBean = new Product();
        productBean.setId(domain.getProduct().id);
        productBean.setName(domain.getProduct().name);
        productBean.setDescription(domain.getProduct().description);
        productBean.setPrice(domain.getProduct().price.doubleValue());
        productBean.setStock(domain.getProduct().stock);

        bean.setStore(storeBean);
        bean.setWarehouse(warehouseBean);
        bean.setProduct(productBean);

        return bean;
    }
}
