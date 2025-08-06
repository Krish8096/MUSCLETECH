package com.example.GYMsupplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.GYMsupplement.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
}
