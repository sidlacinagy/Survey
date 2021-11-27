package com.example.primefaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {

    @GetMapping("/home")
    public String viewIndex() {
        return "home";
    }

}
