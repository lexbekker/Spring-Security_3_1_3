package com.bekker.security.controllers;

import com.bekker.security.entities.User;
import com.bekker.security.repositories.RoleRepository;
import com.bekker.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user with this id : " + id);
        }
        model.addAttribute("user", user.get());
        return "/admin/show";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "/admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user ) {
//        user.setRoles(Set.of(roleRepository.findByName("USER")));
//        user.setPassword();
        user.addRole(roleRepository.findByName("USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user with this id");
        }
        model.addAttribute("user", user.get());
        return "/admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("user") User user) {
        Optional<User> result = userRepository.findById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("No user with this id");
        }
        User editedUser = result.get();
        if (!user.getPassword().equals(editedUser.getPassword())) {
            editedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        editedUser.setEmail(user.getEmail());
        editedUser.setUsername(user.getUsername());
        editedUser.setFirstName(user.getFirstName());
        editedUser.setLastName(user.getLastName());
        editedUser.setRoles(user.getRoles());

        userRepository.save(editedUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user with this id, cant delete");
        }
        userRepository.delete(user.get());
        return "redirect:/admin";
    }




}

