package com.ley.scenario.bean;

import org.springframework.stereotype.Component;

@Component
public class User {
    private String id;

    public User() {

    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
