package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result {

    @SerializedName("status")
    public String status;
    public int totalResults;

    @SerializedName("articles")
    public ArrayList<Articles> articlesArrayList;
}
