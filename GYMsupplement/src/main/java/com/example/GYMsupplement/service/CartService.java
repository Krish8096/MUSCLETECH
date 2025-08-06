package com.example.GYMsupplement.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.GYMsupplement.entity.CartItem;
import com.example.GYMsupplement.entity.Product;


@Service
public class CartService {
	
	 private Map<Integer, CartItem> cart = new HashMap<>();

	    public void addToCart(Product product) {
	    Integer productId = product.getId();
	    if (cart.containsKey(productId)) {
	    cart.get(productId).incrementQuantity();
	    } else {
	    cart.put(productId, new CartItem(product));
	    }
	    }

	    public Collection<CartItem> getCartItems() {
	    return cart.values();
	    }

	    public double getTotalPrice() {
	    double total = 0;
	    for (CartItem item : cart.values()) {
	    total =total+ item.getTotalPrice();
	    }
	    return total;
	    }
	   
	    public void clearCart() {
	    cart.clear();
	    }
	    
}
