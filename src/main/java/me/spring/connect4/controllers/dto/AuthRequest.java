package me.spring.connect4.controllers.dto;

public class AuthRequest {

    private String username;
    private String password;

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

}
