package ir.rightel.bss.eshop.services;

import ir.rightel.bss.eshop.models.Order;
import ir.rightel.bss.eshop.models.Product;
import ir.rightel.bss.eshop.models.ProductGroup;
import ir.rightel.bss.eshop.repositories.GroupRepository;
import ir.rightel.bss.eshop.repositories.OrderRepository;
import ir.rightel.bss.eshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    OrderRepository orderRepository;


    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(long id){
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }




    public List<ProductGroup> getGroups(){
        return groupRepository.findAll();
    }

    public Optional<ProductGroup> getGroup(long id){
        return groupRepository.findById(id);
    }

    public ProductGroup saveGroup(ProductGroup group){
        return groupRepository.save(group);
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrder(long id){
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }

}
