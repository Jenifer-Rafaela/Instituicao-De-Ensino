package com.A3.Trabalho.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/admin/home")
    public ModelAndView adminHome() {
        return new ModelAndView("adminHome");
    }

    @GetMapping("/forbidden")
    public ModelAndView forbidden() {
        return new ModelAndView("forbidden");
    }
}
