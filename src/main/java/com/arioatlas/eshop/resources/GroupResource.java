package com.arioatlas.eshop.resources;

import com.arioatlas.eshop.models.GroupVariant;
import com.arioatlas.eshop.models.ProductGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class GroupResource extends RepresentationModel {
    @JsonProperty
    public long id;
    public String groupName;
    public String price;
    public List<GroupVariant> variants;

    public GroupResource(ProductGroup group){
        this.id = group.getId();
        this.groupName = group.getGroupName();
        this.price = group.getPrice();
        this.variants = group.getGroupVariants();
    }
}
