package com.example.GYMsupplement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GYMsupplement.entity.Category;
import com.example.GYMsupplement.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<Category> getAllCategories() {
        return repo.findAll();
    }

    public void addCategory(Category category) {
        repo.save(category);
    }

    public Category getCategoryById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteCategory(Integer id) {
        repo.deleteById(id);
    }
}
