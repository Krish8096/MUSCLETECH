package com.example.GYMsupplement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.GYMsupplement.entity.OrderForm;
import com.example.GYMsupplement.entity.Product;
import com.example.GYMsupplement.entity.User;
import com.example.GYMsupplement.repository.OrderFormRepository;
import com.example.GYMsupplement.repository.UserRepository;
import com.example.GYMsupplement.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserRepository rep;
	
	@Autowired
	private ProductService service;
	@Autowired
	private OrderFormRepository orderFormRepository;

	
	
	
	@GetMapping("/login")
	public String getLogin()
	{
		//return "login";
		return "Login";
	}
	@GetMapping("/register")
	public String getRegister(Model model)
	{
		model.addAttribute("user",new User());
		return "Register";
	}
	@GetMapping("/subscribe")
    public String showSubscriptionPage() {
        // Return the name of your HTML file without the extension
        return "Subscription";  // If your file is Subscription.html in templates folder
    }
	@PostMapping("registeruser")
	public String newRegistration(@ModelAttribute @Valid User user,BindingResult result,Model m)
	{
		
	user.setRole("user");
	if(result.hasErrors()) {
		return "Register";
	}
	
	User u=rep.save(user);
	if(u!=null &&u.getRole().equalsIgnoreCase("user"))
	{
		m.addAttribute("User Registerd Successfully");
		return "Login";
	}
	else
	{
		m.addAttribute("User not Registered due to some error");
	}
	return "Register";
	}
	@PostMapping("/loginuser")
	public String checkUser(@ModelAttribute User user, Model m, HttpSession session) {
	    User u = rep.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	    if (u == null) {
	        return "failure";
	    }

	    session.setAttribute("user", u); // Save user in session

	    if ("admin".equalsIgnoreCase(u.getRole())) {
	        return "redirect:/admin/getAll";
	    } else if ("user".equalsIgnoreCase(u.getRole())) {
	        return "redirect:/Home"; // Or the home page
	    } else {
	        return "failure";
	    }
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request)
	{
		HttpSession session=request.getSession(false);
		if(session!=null)
		{
			session.invalidate();
		}
		return"redirect:/";
	}
	@GetMapping("/products/{id}")
	public String viewProductDetail(@PathVariable Integer id,Model model) {
		Product product =service.singleProduct(id);
		model.addAttribute("product",product);
		return "product_detail";
	}
	@PostMapping("/place-order")
	public String placeOrder(@Valid @ModelAttribute("orderForm") OrderForm orderForm,
	                         BindingResult result, Model model, HttpSession session) {
	    if (result.hasErrors()) {
	        return "checkout";
	    }

	    orderFormRepository.save(orderForm); // Save order info

	    if ("stripe".equals(orderForm.getPaymentMethod())) {
	        session.setAttribute("orderId", orderForm.getId());
	        return "redirect:/create-checkout-session";
	    } else {
	        return "redirect:/success";
	    }
	}
	@GetMapping("/reset-password")
	public String showResetPasswordForm(Model model) {
	    model.addAttribute("user", new User()); // only username/email and new password
	    return "reset_password"; // your HTML file in templates
	}

	@PostMapping("/reset-password")
	public String processResetPassword(@RequestParam("username") String username,
	                                   @RequestParam("newPassword") String newPassword,
	                                   @RequestParam("confirmPassword") String confirmPassword,
	                                   RedirectAttributes redirectAttributes) {
	    if (!newPassword.equals(confirmPassword)) {
	        redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
	        return "redirect:/reset-password";
	    }

	    User user = rep.findByUsername(username);
	    if (user == null) {
	        redirectAttributes.addFlashAttribute("error", "User not found.");
	        return "redirect:/reset-password";
	    }

	    user.setPassword(newPassword); // You should hash the password in production
	    rep.save(user);

	    redirectAttributes.addFlashAttribute("success", "Password reset successfully. Please login.");
	    return "redirect:/login";
	}

	
	

	
}