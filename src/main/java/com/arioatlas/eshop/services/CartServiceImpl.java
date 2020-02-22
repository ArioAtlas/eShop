package com.arioatlas.eshop.services;

import com.arioatlas.eshop.cahce.Cache;
import com.arioatlas.eshop.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private Cache cache;

    private Order addCartItemsToOrders(List<CartItem> cartItems, Order order){
        cartItems.forEach(cartItem -> {
            Optional<Product> product = shopService.getProduct(cartItem.getProductId());
            int quantity = cartItem.getQuantity();
            long variantId = cartItem.getVariantId();
            if(product.isPresent()) {
                for (int i = 0; i < quantity; i++) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product.get());

                    if (variantId > 0) {
                        GroupVariant variant = new GroupVariant();
                        variant.setId(variantId);
                        orderItem.setGroupVariant(variant);
                    }

                    orderItem.setOrder(order);
                    order.getItems().add(orderItem);
                }
            }
        });

        return order;
    }

    @Override
    public String createNewCart() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void addProduct(String cartId, CartItem cartItem) {
        cache.addItemToList(cartId,cartItem);
    }

    @Override
    public void removeProduct(String cartId, String productId) {
        CartItem item = new CartItem();
        item.setProductId(Long.parseLong(productId));
        cache.removeItemFromList(cartId,item);
    }

    @Override
    public void setProductQuantity(String cartId, String productId, int quantity) {
        List<CartItem> list = (List) cache.getList(cartId,CartItem.class);
        list.forEach(cartItem -> {
            if(cartItem.getProductId() == Long.parseLong(productId)){
                cartItem.setQuantity(quantity);
                cache.removeItemFromList(cartId,cartItem);
                cache.addItemToList(cartId,cartItem);
            }
        });
    }

    @Override
    public Set<CartItem> getItems(String cartId) {
        return new HashSet<CartItem>( (List)cache.getList(cartId,CartItem.class));
    }

    @Override
    public Order createOrder(String cartId, Order order) {
        List<CartItem> items = (List) cache.getList(cartId,CartItem.class);

        order = addCartItemsToOrders(items,order);

        if(order == null){
            System.out.println("Error in setting the order!");
        }

        order = shopService.saveOrder(order);
        cache.removeItem(cartId);

        return order;
    }
}
