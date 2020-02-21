package ir.rightel.bss.eshop.controllers;

import ir.rightel.bss.eshop.models.Order;
import ir.rightel.bss.eshop.resources.OrderResource;
import ir.rightel.bss.eshop.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController extends CoreController {

    @Autowired
    private ShopService shopService;

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
    public Order create(@RequestBody Order order){
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
    public Order edit(@PathVariable("id") long id, @RequestBody Order order){
        Optional<Order> updated = shopService.getOrder(id);
        return updated.map(value -> shopService.saveOrder(value)).orElse(null);
    }
}
