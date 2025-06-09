package com.coworking.reservationsystem.graphql.input;

public class CreateUserInput {
    private String email;
    private String password;
    private String name;
    
    // Constructors
    public CreateUserInput() {}
    
    public CreateUserInput(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    
    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}