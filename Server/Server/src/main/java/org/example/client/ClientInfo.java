package org.example.clientofnetwork.model.passingAndRecievingData.client;

import java.time.Instant;

public class ClientInfo {
    private volatile String token;
    private volatile Long userID;
    private volatile String userName;
    private volatile Instant password;

    public String getToken() {return token;}

    public void setToken(String token) {this.token = token;}

    public Long getUserID() {return userID;}

    public void setUserID(Long userID) {this.userID = userID;}

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public Instant getPassword() {return password;}

    public void setPassword(Instant password) {this.password = password;}
}
