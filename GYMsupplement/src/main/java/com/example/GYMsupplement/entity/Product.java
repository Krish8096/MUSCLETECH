package com.example.GYMsupplement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	
	@NotBlank(message="Product name cannot be blank")
	private String name;
	@Positive(message="Product price cannot be zero")
	@Min(value=1,message="Price must be 1")
	@Max(value=10000,message="Price must be lower than 10000")
	private double price;
	@NotBlank(message="Product Description cannot be blank")
	@Size(min=10,message="Description must be atleast 10 charachter")
	private String description;
	private String imageurl;
	
	
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Product(int id, String name, double price, String description) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}
	public Product() {
		super();
	}
	 @ManyToOne
	    @JoinColumn(name = "category_id")
	    private Category category;

	 public Category getCategory() {
		    return category;
		}

		public void setCategory(Category category) {
		    this.category = category;
		}
		

	
}
