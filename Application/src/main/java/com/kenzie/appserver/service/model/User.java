package com.kenzie.appserver.service.model;

public class User {
    private final String userId;
    private final String firstName; //should we make this final
    private final String lastName; //should we make this final

    public User(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {return lastName;}
}
