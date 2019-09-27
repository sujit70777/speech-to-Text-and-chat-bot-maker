package com.example.sujit007.wwww;

/**
 * Created by Sujit007 on 4/30/2017.
 */

public class Data {

    private int id ;
    private String text;
    private String replay;

    public Data() {
    }

    public Data(int id, String text, String replay) {
        this.id = id;
        this.text = text;
        this.replay = replay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }
}
