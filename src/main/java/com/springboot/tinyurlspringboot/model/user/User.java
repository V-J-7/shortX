package com.springboot.tinyurlspringboot.model.user;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Column(name="email",nullable=false, unique = true)
    String email;

    @Column(name="password",nullable=false)
    String passwordHash;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id",unique=true)
    Long userId;

    public User(String email, String password) {
        this.email = email;
        this.passwordHash = password;
    }

    public User() {

    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.passwordHash = password;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }
    public Long getUserId() {
        return userId;
    }
}
