package com.example.demo;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/")
    public String hreet(HttpServletRequest request){
        return "Welcom" + request.getSession().getId();
    }


}
