package com.ley.scenario.bean;

public class Person {
    public Person() {

    }

    public Person(String firstName) {
        this.firstName = firstName;
    }

    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
