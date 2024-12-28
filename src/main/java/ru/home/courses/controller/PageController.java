package ru.home.courses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/register")
    public String redirectToRegisterPage() {
        return "redirect:/register.html"; // Переадресация на файл register.html
    }
}
