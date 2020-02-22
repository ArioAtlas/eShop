package com.arioatlas.eshop.controllers;

import com.arioatlas.eshop.models.CartItem;
import com.arioatlas.eshop.models.Order;
import com.arioatlas.eshop.resources.OrderResource;
import com.arioatlas.eshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/cart")
public class CartController extends CoreController {

    @Autowired
    CartService cartService;

    @Autowired
    Validator orderValidator;

    @InitBinder void initBinder(WebDataBinder binder){
        binder.addValidators(orderValidator);
    }

    @PostMapping("/")
    public String create(){
        return cartService.createNewCart();
    }

    @GetMapping("/{id}")
    public Set<CartItem> getCartItems(@PathVariable("id") String cartId){
        return cartService.getItems(cartId);
    }

    @PostMapping("/{id}")
    public String addProduct(@PathVariable("id") String cartId, @RequestBody CartItem cartItem){
        cartService.addProduct(cartId,cartItem);
        return "added successfully";
    }

    @DeleteMapping("/{id}/{pid}")
    public String removeItem(@PathVariable("id") String cartId, @PathVariable("pid") String productId){
        cartService.removeProduct(cartId,productId);
        return "removed successfully";
    }

    @PostMapping("/{id}/quantity")
    public String updateProductQuantity(@PathVariable("id") String cartId, @RequestBody CartItem cartItem){
        String productId = Long.toString(cartItem.getProductId());
        cartService.setProductQuantity(cartId,productId,cartItem.getQuantity());

        return "Quantity successfully Updated";
    }

    @PostMapping("{id}/order")
    public OrderResource createOrder(@PathVariable("id") String cartId, @RequestBody @Valid Order order){
        if (order!=null){
            OrderResource orderResource = new OrderResource(cartService.createOrder(cartId,order));
            orderResource.add(createHateoasLink(order.getId()));

            return orderResource;
        }
        else{
            return null;
        }
    }
}
