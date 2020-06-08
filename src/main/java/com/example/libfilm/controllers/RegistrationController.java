package com.example.libfilm.controllers;

import com.example.libfilm.dao.User;
import com.example.libfilm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrationController  {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("g-recaptcha-response") String captchaResponce,
            @Valid User user, BindingResult bindingResult, Model model
    ) {

        System.out.println("go");
        userService.registration(user, bindingResult, captchaResponce);
        return "redirect:/";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code) {

        System.out.println(userService.activateUser(code) ? "success activation" : "error activation");

        return "redirect:/";
    }
}
