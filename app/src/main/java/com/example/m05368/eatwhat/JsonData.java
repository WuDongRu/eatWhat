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

    @SerializedName("S_price")
    private String S_price;

    @SerializedName("S__longitude")
    private String S__longitude;

    @SerializedName("S_latitude")
    private String S_latitude;

    @SerializedName("T_name")
    private String T_name;


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

    public String getS_price() {
        return S_price;
    }

    public void setS_price(){
        this.S_price = S_price;
    }

    public String getS_address() {
        return S_address;
    }

    public void setS_address(){
        this.S_address = S_address;
    }

    public String getS__longitude() {
        return S__longitude;
    }

    public void setS__longitude(){
        this.S__longitude = S__longitude;
    }

    public String getS_latitude() {
        return S_latitude;
    }

    public void setS_latitude(){
        this.S_latitude = S_latitude;
    }

    public String getT_name() {
        return T_name;
    }

    public void setT_name(){
        this.T_name = T_name;
    }
}