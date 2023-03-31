package me.spring.connect4.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {


    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/game")
    public String game(){
        return "game.html";
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile.html";
    }

}
