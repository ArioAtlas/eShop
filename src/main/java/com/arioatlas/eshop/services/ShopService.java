package com.arioatlas.eshop.services;

import com.arioatlas.eshop.models.Order;
import com.arioatlas.eshop.models.Product;
import com.arioatlas.eshop.models.ProductGroup;
import com.arioatlas.eshop.repositories.GroupRepository;
import com.arioatlas.eshop.repositories.OrderRepository;
import com.arioatlas.eshop.repositories.ProductRepository;
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
