package com.example.GYMsupplement.entity;

import com.example.GYMsupplement.entity.Product;

public class CartItem {

private Product product;
private int quantity;

public CartItem(Product product)
{
	this.product=product;
	quantity=1;
}

public void incrementQuantity() 
{
	this.quantity++;
}

public int getQuantity()
{
	return quantity;
}
public Product getProduct() 
{
	return product;
}
public double getTotalPrice()
{
	return product.getPrice()*quantity;
}
}
