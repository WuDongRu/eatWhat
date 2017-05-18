package com.example.m05368.eatwhat.Json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by M05368 on 2017/5/15.
 */

public class JsonComment {

    @SerializedName("S_id")
    private int S_id;

    @SerializedName("S_name")
    private String S_name;

    @SerializedName("Comment")
    private List Comment;


    public int getS_id() {
        return S_id;
    }

    public void setS_id(){
        this.S_id = S_id;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(){
        this.S_name = S_name;
    }

    public List getComment() {
        return Comment;
    }

    public void setComment(){
        this.Comment = Comment;
    }

}
