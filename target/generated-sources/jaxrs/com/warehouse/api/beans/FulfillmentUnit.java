
package com.warehouse.api.beans;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "store",
    "warehouse",
    "product"
})
@Generated("jsonschema2pojo")
public class FulfillmentUnit {

    @JsonProperty("id")
    private Long id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("store")
    private Store store;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("warehouse")
    private Warehouse warehouse;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("product")
    private Product product;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("store")
    public Store getStore() {
        return store;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("store")
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("warehouse")
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("warehouse")
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("product")
    public Product getProduct() {
        return product;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("product")
    public void setProduct(Product product) {
        this.product = product;
    }

}
