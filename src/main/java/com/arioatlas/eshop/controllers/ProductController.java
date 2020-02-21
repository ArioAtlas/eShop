package com.arioatlas.eshop.controllers;

import com.arioatlas.eshop.models.Product;
import com.arioatlas.eshop.services.ShopService;
import com.arioatlas.eshop.storage.StorageService;
import com.arioatlas.eshop.resources.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController extends CoreController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private StorageService storageService;

    @Autowired
    Validator productValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(productValidator);
    }


    @GetMapping
    public List<ProductResource> index(){
        List<Product> products = shopService.getProducts();
        List<ProductResource> resource = new ArrayList<>();

        products.forEach((item)->{
            ProductResource productResource = new ProductResource(item);
            productResource.add(createHateoasLink(item.getId()));

            resource.add(productResource);
        });

        return resource;
    }

    @PostMapping
    public Product create(@RequestBody @Valid Product product){
        return shopService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public ProductResource view(@PathVariable("id") long id){
        Optional<Product> product = shopService.getProduct(id);

        if(!product.isPresent()){
            return null;
        }

        ProductResource productResource = new ProductResource(product.get());
        productResource.add(createHateoasLink(product.get().getId()));

        return productResource;
    }

    @PostMapping("/{id}")
    public Product edit(@PathVariable("id") long id, @RequestBody @Valid Product product){
        Optional<Product> updated = shopService.getProduct(id);

        if(!updated.isPresent()){
            return null;
        }

        updated.get().setName(product.getName());
        updated.get().setDescription(product.getDescription());
        updated.get().setPrice(product.getPrice());

        return shopService.saveProduct(updated.get());
    }
}
