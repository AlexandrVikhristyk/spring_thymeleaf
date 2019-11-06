package com.example.demo.controller;

import com.example.demo.entity.CustomUser;
import com.example.demo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class UserController {
    private UserService userService;
    private Validator userValidator;

    public UserController(UserService userService, Validator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/")
    public String index(Model model){
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String login = user.getUsername();
        CustomUser dbUser = userService.findByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());

        return "index";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(required = false) String email) {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String login = user.getUsername();
        userService.updateUser(email);

        return "redirect:/";
    }

    @PostMapping("/newuser")
    public String update(@Valid @ModelAttribute("user") CustomUser user,
                         BindingResult bindingResult,
                         Model model) {
        System.out.println("BEFORE VALIDATION MAZEFACKA");
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("email", "KJfk");
            System.out.println("******************************************************");
            System.out.println("666666666666666666666666666666666666666666666666666666");
            System.out.println("999999999999999999999999999999999999999999999999999999");
            System.out.println("******************************************************");
            return "register"; // to person.jsp page
        }
        model.addAttribute("email", user.getEmail());


        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(Model model){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }
}
