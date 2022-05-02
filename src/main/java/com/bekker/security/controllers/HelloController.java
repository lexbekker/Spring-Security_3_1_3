package com.bekker.security.controllers;

import com.bekker.security.entities.User;
import com.bekker.security.repositories.UserRepository;
import com.bekker.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC application");
		messages.add("5.2.0 version by april'22 ");
		model.addAttribute("messages", messages);
		return "redirect:/login";
	}

	@GetMapping("/user")
	public String userPage(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("log", userService.findByUsername(principal.getName()));
		model.addAttribute("user", user);
		return "user";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
}