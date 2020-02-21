package com.arioatlas.eshop.resources;

import com.arioatlas.eshop.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class ProductResource extends RepresentationModel {
    @JsonProperty
    public long id;
    public String name;
    public String price;
    public String description;
    public Object group;

    public ProductResource(Product product){
        id = product.getId();
        name = product.getName();
        price = product.getPrice();
        description = product.getDescription();
        group = product.getGroup();
    }
}
