package com.example.josepablo.myapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Titular {

    @JsonProperty("postID")
    private int postID;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("title")
    private String title;

    @JsonProperty("cont")
    private String content;

    @JsonProperty("thumbNail")
    private int thumbNail;



    public Titular(int p_postID, String p_publisher,String p_title, String p_content, int p_thumbNail){
        this.postID = p_postID;
        this.publisher = p_publisher;
        this.title = p_title;
        this.content = p_content;
        this.thumbNail = p_thumbNail;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(int thumbNail) {
        this.thumbNail = thumbNail;
    }
}
