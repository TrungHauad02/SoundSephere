package com.example.soundsephere.model;

import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumTypePlaylist;

import java.util.List;

public class Playlists {
    private int id;
    private String name;
    private int user_id;

    private int number_of_songs;
    private EnumTypePlaylist type = EnumTypePlaylist.PLAYLIST;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public EnumTypePlaylist getType() {
        return type;
    }

    public void setType(EnumTypePlaylist type) {
        this.type = type;
    }

    public EnumStatus getStatus() {
        return status;
    }

    public void setStatus(EnumStatus status) {
        this.status = status;
    }

    public int getNumber_of_songs() {
        return number_of_songs;
    }

    public void setNumber_of_songs(int number_of_songs) {
        this.number_of_songs = number_of_songs;
    }
}
