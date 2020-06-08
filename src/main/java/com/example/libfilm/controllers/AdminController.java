package com.example.libfilm.controllers;

import com.example.libfilm.utils.BetaFunctions;
import com.example.libfilm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AdminController implements BetaFunctions {

    @Autowired
    AdminService adminService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin")
    public String admin(Model model) { return "admin"; }

    @PostMapping("/admin")
    public String addFilm(
            Model model,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String filmName,
            @RequestParam("file") MultipartFile file)  throws IOException {

        if(adminService.addFilm(model, genre, filmName, file)) {
            return "admin";
        }

        return "admin";
    }

}
