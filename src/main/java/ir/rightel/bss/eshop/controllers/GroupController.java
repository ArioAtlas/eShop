package ir.rightel.bss.eshop.controllers;

import ir.rightel.bss.eshop.models.ProductGroup;
import ir.rightel.bss.eshop.resources.GroupResource;
import ir.rightel.bss.eshop.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController extends CoreController {

    @Autowired
    ShopService shopService;

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
    public ProductGroup create(@RequestBody ProductGroup group){
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
    public ProductGroup edit(@PathVariable("id") long id,@RequestBody ProductGroup group){
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
