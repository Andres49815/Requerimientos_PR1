package com.example.josepablo.myapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccount {
    @JsonProperty("userID")
    private String userID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("profilePic")
    private int profilePic;

    public UserAccount(String userID, String name, String lastName, int profilePic) {
        this.userID = userID;
        this.name = name;
        this.lastName = lastName;
        this.profilePic = profilePic;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
}
