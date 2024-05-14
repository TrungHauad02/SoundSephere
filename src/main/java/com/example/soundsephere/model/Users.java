package com.example.soundsephere.model;


import com.example.soundsephere.enumModel.EnumRole;
import com.example.soundsephere.enumModel.EnumSex;
import com.example.soundsephere.enumModel.EnumUserStatus;

import java.util.Date;

public class Users {
    private String id;
    private String name;
    private EnumSex sex;
    private String description;
    private String username;
    private String email;
    private String password;
    private EnumRole role;
    private EnumUserStatus status;

    public Users() {
    }

    public Users(String id, String name, EnumSex sex,
                 String description, String username, String email, String password, EnumRole role, EnumUserStatus status) {
        super();
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.description = description;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumSex getSex() {
        return sex;
    }

    public void setSex(EnumSex sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setRole(EnumRole role) {
        this.role = role;
    }

    public EnumUserStatus getStatus() {
        return status;
    }

    public void setStatus(EnumUserStatus status) {
        this.status = status;
    }
}


