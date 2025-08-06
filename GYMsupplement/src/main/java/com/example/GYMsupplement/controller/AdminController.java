package com.example.GYMsupplement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.GYMsupplement.entity.Product;
import com.example.GYMsupplement.service.CategoryService;
import com.example.GYMsupplement.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private ProductService service;

    @Autowired
    private CategoryService categoryservice;

    @GetMapping("/Home")
    public String GymHome() {
        return "Home";
    }

    @GetMapping("/productsss")
    public String GymList() {
        return "Listproducts";
    }

    @GetMapping("/admin/getAll")
    public String getAll(Model m) {
        List<Product> plist = service.getAllProducts();
        m.addAttribute("productlist", plist);
        return "admin/ListproductsAdmin";
    }

    @GetMapping("/admin/new")
    public String viewProductPage(Model m) {
        Product p = new Product();
        m.addAttribute("product", p);
        m.addAttribute("categories", categoryservice.getAllCategories());
        return "admin/new";
    }

    @PostMapping("/admin/save")
    public String saveProduct(@ModelAttribute @Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryservice.getAllCategories());
            return "admin/new";
        }
        service.addProduct(product);
        return "redirect:/admin/getAll";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
        return "redirect:/admin/getAll";
    }

    @GetMapping("/admin/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model m) {
        Product p = service.singleProduct(id);
        m.addAttribute("product", p);
        m.addAttribute("categories", categoryservice.getAllCategories());
        return "admin/update";
    }

    @PostMapping("/admin/update/{id}")
    public String updateProduct(@PathVariable Integer id,
                                @ModelAttribute("product") @Valid Product uproduct,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryservice.getAllCategories());
            return "admin/update";
        }
        service.updateProduct(id, uproduct);
        return "redirect:/admin/getAll";
    }
}
