package com.example.login_password.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
    private String username;
    private int age;
    private String gender;
    private boolean isLoggedin;
    @NotNull
    private String role;

    public User() {
        this.role = "user"; // Set default role to 'user'
    }

    public void setRole(String role) {
        this.role = role;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public boolean isLoggedin() {
        return isLoggedin;
    }
    public void setLoggedin(boolean isLoggedin) {
        this.isLoggedin = isLoggedin;
    }

}
