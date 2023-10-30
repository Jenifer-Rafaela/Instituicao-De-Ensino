package com.A3.Curso.Controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

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
    public ModelAndView forbidden(){
        return new ModelAndView("forbidden");
    }
}
