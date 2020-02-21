package ir.rightel.bss.eshop.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.rightel.bss.eshop.models.GroupVariant;
import ir.rightel.bss.eshop.models.ProductGroup;
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
