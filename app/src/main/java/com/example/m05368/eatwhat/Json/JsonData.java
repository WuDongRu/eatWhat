package com.example.m05368.eatwhat.Json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    private List T_name;

    @SerializedName("S_phone")
    private String S_phone;

    @SerializedName("S_opentime")
    private String S_opentime;

    @SerializedName("S_closetime")
    private String S_closetime;

    @SerializedName("P_photes")
    private List P_photes;


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

    public List getT_name() {
        return T_name;
    }

    public void setT_name(){
        this.T_name = T_name;
    }

    public String getS_phone() {
        return S_phone;
    }

    public void setS_phone(){
        this.S_phone = S_phone;
    }

    public void setS_opentime(){
        this.S_opentime = S_opentime;
    }

    public String getS_opentime() {
        return S_opentime;
    }

    public void setS_closetime(){
        this.S_closetime = S_closetime;
    }

    public String getS_closetime() {
        return S_closetime;
    }

    public List getP_photes() {
        return P_photes;
    }

    public void setP_photes(){
        this.P_photes = P_photes;
    }

}