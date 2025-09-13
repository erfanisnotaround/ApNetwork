package org.example.clientofnetwork.model.passingAndRecievingData.client;

import java.time.Instant;

public class ClientInfo {
    private volatile String userName;
    private volatile String id;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
