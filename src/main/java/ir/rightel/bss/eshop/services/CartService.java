package ir.rightel.bss.eshop.services;

import ir.rightel.bss.eshop.models.CartItem;
import ir.rightel.bss.eshop.models.Order;

import java.util.Set;

public interface CartService {
    public String createNewCart();
    public void addProduct(String cartId, CartItem cartItem);
    public void removeProduct(String cartId, String productId);

    public void setProductQuantity(String cartId, String productId, int quantity);
    public Set<CartItem> getItems(String cartId);
    public Order createOrder(String cartId, Order order);
}
