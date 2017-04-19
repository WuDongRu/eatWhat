package com.example.m05368.eatwhat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by M05368 on 2017/4/19.
 */

public class JsonData {
    @SerializedName("S_id")
    private int S_id;

    @SerializedName("S_name")
    private String S_name;

    @SerializedName("S_address")
    private String S_address;

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

    public String getS_address() {
        return S_address;
    }

    public void setS_address(){
        this.S_address = S_address;
    }
}