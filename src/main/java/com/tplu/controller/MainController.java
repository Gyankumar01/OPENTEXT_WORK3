package com.tplu.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping ("/")
    @ResponseBody
    public String welcome() {
        return "Welcome to My First Web Application!";
    }

}
//This Spring MVC controller returns a welcome message when users access the root URL ("/") of the web application.


