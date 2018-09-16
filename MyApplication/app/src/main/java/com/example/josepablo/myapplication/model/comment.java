package com.example.josepablo.myapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class comment {
    @JsonProperty("userID")
    private String userID;

    @JsonProperty("content")
    private String content;

    @JsonProperty("thumbNail")
    private int thumbNail;

    public comment(String pUser, String pContent, int pThumbNail){
        this.userID = pUser;
        this.content = pContent;
        this.thumbNail = pThumbNail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
