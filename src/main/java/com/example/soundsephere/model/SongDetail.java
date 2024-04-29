package com.example.soundsephere.model;

import java.util.Date;

public class SongDetail {
    private int song_id;
    private String written_by;
    private String produced_by;
    private Date date_release;

    public SongDetail(int song_id, String written_by, String produced_by, java.sql.Date date_release) {
        this.song_id = song_id;
        this.written_by = written_by;
        this.produced_by = produced_by;
        this.date_release = date_release;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public String getWritten_by() {
        return written_by;
    }

    public void setWritten_by(String written_by) {
        this.written_by = written_by;
    }

    public String getProduced_by() {
        return produced_by;
    }

    public void setProduced_by(String produced_by) {
        this.produced_by = produced_by;
    }

    public Date getDate_release() {
        return date_release;
    }

    public void setDate_release(Date date_release) {
        this.date_release = date_release;
    }

}
