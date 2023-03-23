package me.spring.connect4.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {


    @GetMapping("")
    public String index(){
        return "index";
    }


}
