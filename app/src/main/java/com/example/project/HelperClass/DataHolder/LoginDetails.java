package com.example.project.HelperClass.DataHolder;
public class LoginDetails {
    private String username;
    private String password;

    public LoginDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    // Getters and setters...
}
