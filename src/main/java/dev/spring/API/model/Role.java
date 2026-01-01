package dev.spring.API.model;

public enum Role {
    ADMIN,
    USER;

    public String authority(){
        return "ROLE_" + name();
    }
}


