package com.arioatlas.eshop.controllers;

import com.arioatlas.eshop.models.ProductGroup;
import com.arioatlas.eshop.resources.GroupResource;
import com.arioatlas.eshop.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController extends CoreController {

    @Autowired
    ShopService shopService;

    @Autowired
    Validator groupValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(groupValidator);
    }

    @GetMapping
    public List<GroupResource> index(){
        List<ProductGroup> groups = shopService.getGroups();
        List<GroupResource> resource = new ArrayList<>();

        groups.forEach(group -> {
            GroupResource groupResource = new GroupResource(group);
            groupResource.add(createHateoasLink(group.getId()));

            resource.add(groupResource);
        });

        return resource;
    }

    @PostMapping
    public ProductGroup create(@RequestBody @Valid ProductGroup group){
        if(group.getGroupVariants()!=null){
            group.getGroupVariants().forEach(groupVariant -> {
                groupVariant.setGroup(group);
            });
        }
        return shopService.saveGroup(group);
    }

    @GetMapping("/{id}")
    public GroupResource view(@PathVariable("id") long id){
        Optional<ProductGroup> group = shopService.getGroup(id);
        if(group.isPresent()){
           GroupResource groupResource = new GroupResource(group.get());
           groupResource.add(createHateoasLink(group.get().getId()));

           return groupResource;
        }

        return null;
    }

    @PostMapping("/{id}")
    public ProductGroup edit(@PathVariable("id") long id,@RequestBody @Valid ProductGroup group){
        Optional<ProductGroup> updated = shopService.getGroup(id);

        if(!updated.isPresent()){
            return null;
        }

        updated.get().setGroupName(group.getGroupName());
        updated.get().setPrice(group.getPrice());
        updated.get().setGroupVariants(group.getGroupVariants());

        if(updated.get().getGroupVariants() !=null){
            updated.get().getGroupVariants().forEach(groupVariant -> {
                groupVariant.setGroup(updated.get());
            });
        }

        return shopService.saveGroup(updated.get());
    }
}
