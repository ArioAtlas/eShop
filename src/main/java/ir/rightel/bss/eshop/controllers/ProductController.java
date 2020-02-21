package ir.rightel.bss.eshop.controllers;

import ir.rightel.bss.eshop.models.Product;
import ir.rightel.bss.eshop.resources.ProductResource;
import ir.rightel.bss.eshop.services.ShopService;
import ir.rightel.bss.eshop.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Product create(@RequestBody Product product){
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
    public Product edit(@PathVariable("id") long id, @RequestBody Product product){
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
