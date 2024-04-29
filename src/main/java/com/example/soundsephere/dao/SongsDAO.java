package com.example.soundsephere.dao;

import com.example.soundsephere.model.Songs;

import java.util.List;

public class SongsDAO extends SoundSysDAO<Songs, Integer>{
    public boolean insert(Songs entity){
        return false;
    }
    public boolean update(Songs entity){
        return false;
    }
    public boolean delete(Integer id){
        return false;
    }

    public Songs selectById(Integer id){
        return null;
    }

    public List<Songs> selectAll(){
        return null;
    }

    protected List<Songs> selectBySql(String sql, Object...args){
        return null;
    }
}
