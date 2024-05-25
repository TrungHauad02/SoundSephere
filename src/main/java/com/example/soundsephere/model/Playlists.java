package com.example.soundsephere.model;

import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumTypePlaylist;

public class Playlists {
    private String id;
    private String name;
    private int number_of_songs;
    private String user_id;
    private EnumTypePlaylist type = EnumTypePlaylist.PLAYLIST;
    private EnumStatus status = EnumStatus.AVAILABLE;

    private Users users;

    // get artist name
    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getUsers() {
        return users;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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
