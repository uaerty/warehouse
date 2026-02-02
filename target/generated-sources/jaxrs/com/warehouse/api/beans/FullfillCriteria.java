
package com.warehouse.api.beans;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "warehouseBusinessUnitCode",
    "storeId",
    "productId"
})
@Generated("jsonschema2pojo")
public class FullfillCriteria {

    @JsonProperty("warehouseBusinessUnitCode")
    private String warehouseBusinessUnitCode;
    @JsonProperty("storeId")
    private String storeId;
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("warehouseBusinessUnitCode")
    public String getWarehouseBusinessUnitCode() {
        return warehouseBusinessUnitCode;
    }

    @JsonProperty("warehouseBusinessUnitCode")
    public void setWarehouseBusinessUnitCode(String warehouseBusinessUnitCode) {
        this.warehouseBusinessUnitCode = warehouseBusinessUnitCode;
    }

    @JsonProperty("storeId")
    public String getStoreId() {
        return storeId;
    }

    @JsonProperty("storeId")
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @JsonProperty("productId")
    public String getProductId() {
        return productId;
    }

    @JsonProperty("productId")
    public void setProductId(String productId) {
        this.productId = productId;
    }

}
