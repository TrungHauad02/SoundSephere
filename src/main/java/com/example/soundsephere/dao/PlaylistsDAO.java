package com.example.soundsephere.dao;

import com.example.soundsephere.model.Playlists;

import java.util.List;

public class PlaylistsDAO  extends SoundSysDAO<Playlists, Integer>{
    public boolean insert(Playlists entity){
        return false;
    }
    public boolean update(Playlists entity){
        return false;
    }
    public boolean delete(Integer id){
        return false;
    }

    public Playlists selectById(Integer id){
        return null;
    }

    public List<Playlists> selectAll(){
        return null;
    }

    protected List<Playlists> selectBySql(String sql, Object...args){
        return null;
    }
}
