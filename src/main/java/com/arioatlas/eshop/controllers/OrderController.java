package com.arioatlas.eshop.controllers;

import com.arioatlas.eshop.models.Order;
import com.arioatlas.eshop.services.ShopService;
import com.arioatlas.eshop.resources.OrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController extends CoreController {

    @Autowired
    private ShopService shopService;

    @Autowired
    Validator orderValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(orderValidator);
    }

    @GetMapping
    public List<OrderResource> index(){
        List<Order> orders = shopService.getOrders();
        List<OrderResource> resource = new ArrayList<>();

        if (orders != null) {
            orders.forEach((order -> {
                OrderResource orderResource = new OrderResource(order);
                orderResource.add(createHateoasLink(order.getId()));

                resource.add(orderResource);
            }));
        }

        return resource;
    }

    @PostMapping
    public Order create(@RequestBody @Valid Order order){
        if (order.getItems() != null) {
            order.getItems().forEach(item->{
                item.setOrder(order);
            });
        }

        return shopService.saveOrder(order);
    }

    @GetMapping("/{id}")
    public OrderResource view(@PathVariable("id") long id){
        Optional<Order> order = shopService.getOrder(id);
        if(order.isPresent()){
            OrderResource orderResource = new OrderResource(order.get());
            orderResource.add(createHateoasLink(order.get().getId()));
            return orderResource;
        }

        return null;
    }

    @PostMapping("/{id}")
    public Order edit(@PathVariable("id") long id, @RequestBody @Valid Order order){
        Optional<Order> updated = shopService.getOrder(id);
        return updated.map(value -> shopService.saveOrder(value)).orElse(null);
    }
}
