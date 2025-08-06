package com.example.GYMsupplement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.GYMsupplement.entity.Category;
import com.example.GYMsupplement.entity.OrderForm;
import com.example.GYMsupplement.entity.Product;
import com.example.GYMsupplement.entity.User;
import com.example.GYMsupplement.repository.ProductRepository;
import com.example.GYMsupplement.service.CartService;
import com.example.GYMsupplement.service.CategoryService;
import com.example.GYMsupplement.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository prepo;

    @Autowired
    private CartService cartservice;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String viewProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int categoryId,
            @RequestParam(required = false) String keyword,
            Model model) {

        int pageSize = 6;
        Page<Product> productPage;

        if (keyword != null && !keyword.isEmpty()) {
            productPage = productService.searchProducts(keyword, categoryId, page, pageSize);
            model.addAttribute("keyword", keyword);
        } else {
            productPage = productService.getProductsByCategory(categoryId, page, pageSize);
        }

        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("productlist", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);

        return "Listproducts";
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Product product = prepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        cartservice.addToCart(product);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart() {
        cartservice.clearCart();
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartservice.getCartItems());
        model.addAttribute("total", cartservice.getTotalPrice());
        model.addAttribute("orderForm", new OrderForm());
        return "cart";
    }

    @GetMapping("/Checkout")
    public String Checkout(Model m) {
        OrderForm orderForm = new OrderForm();
        m.addAttribute("orderForm", orderForm);
        return "Checkout";
    }
}
