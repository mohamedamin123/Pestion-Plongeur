package com.example.plongeur.model;

import java.util.List;

public class User {

    private String idUser;
    private String email;
    private String role;



    public User() {

    }

    public User( String email, String role) {

        this.email = email;
        this.role = role;
    }

    public User(String idUser,String email,String role) {
        this(email,role);
        this.idUser = idUser;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
