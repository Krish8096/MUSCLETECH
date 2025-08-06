package com.example.GYMsupplement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.GYMsupplement.entity.Product;
import com.example.GYMsupplement.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public void addProduct(Product newproduct) {
        repo.save(newproduct);
    }

    public void deleteProduct(Integer id) {
        repo.deleteById(id);
    }

    public Product singleProduct(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void updateProduct(Integer id, Product uproduct) {
        uproduct.setId(id);
        repo.save(uproduct);
    }

    public List<Product> searchProduct(String keyword) {
        return repo.searchByKeyword(keyword);
    }

    public List<Product> getProductsByCategory(Integer categoryId) {
        return repo.findByCategoryId(categoryId);
    }

    // ✅ NEW METHOD — Paged product fetch by category
    public Page<Product> getProductsByCategory(int categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (categoryId == 0) {
            return repo.findAll(pageable);
        } else {
            return repo.findByCategoryId(categoryId, pageable);
        }
    }

    // ✅ NEW METHOD — Paged search by keyword + category
    public Page<Product> searchProducts(String keyword, int categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (categoryId == 0) {
            return repo.searchByKeyword(keyword, pageable);
        } else {
            return repo.searchByKeywordAndCategoryId(keyword, categoryId, pageable);
        }
    }
}
