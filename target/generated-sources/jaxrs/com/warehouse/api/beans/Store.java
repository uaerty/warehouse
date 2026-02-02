
package com.warehouse.api.beans;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "quantityProductsInStock"
})
@Generated("jsonschema2pojo")
public class Store {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quantityProductsInStock")
    private Integer quantityProductsInStock;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("quantityProductsInStock")
    public Integer getQuantityProductsInStock() {
        return quantityProductsInStock;
    }

    @JsonProperty("quantityProductsInStock")
    public void setQuantityProductsInStock(Integer quantityProductsInStock) {
        this.quantityProductsInStock = quantityProductsInStock;
    }

}
