package com.example.GYMsupplement.repository;

import com.example.GYMsupplement.entity.OrderForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFormRepository extends JpaRepository<OrderForm, Long> {
}