package org.example.core.domain;

import org.example.sameInfoes.UserPlaceState;

public final class User {
    private final String id, username, salt, passHash;
    private UserPlaceState userPlaceState;

    public User(String id, String username, String salt, String passHash){
        this.id=id; this.username=username; this.salt=salt; this.passHash=passHash;
    }
    public String getId(){ return id; }
    public String getUsername(){ return username; }
    public String getSalt(){ return salt; }
    public String getPassHash(){ return passHash; }

    public UserPlaceState getUserPlaceState() {
        return userPlaceState;
    }

    public void setUserPlaceState(UserPlaceState userPlaceState) {
        this.userPlaceState = userPlaceState;
    }
}
