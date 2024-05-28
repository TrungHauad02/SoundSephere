package com.example.soundsephere.model;

import com.example.soundsephere.enumModel.EnumStatus;

public class Genre {
    private int id;
    private String name;
    private EnumStatus status = EnumStatus.AVAILABLE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumStatus getStatus() {
        return status;
    }

    public void setStatus(EnumStatus status) {
        this.status = status;
    }

}