package org.example.core.domain;

import com.fasterxml.jackson.annotation.*;
import org.example.sameInfoes.UserPlaceState;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)      // tolerate extra fields in file
public final class User {

    private final String id;
    private final String username;
    private final String salt;
    private final String passHash;
    private final String token;

    @JsonIgnore                                   // do NOT persist session UI state
    private UserPlaceState userPlaceState;

    @JsonCreator                                   // <— tell Jackson to use this constructor
    public User(@JsonProperty("id")       String id,
                @JsonProperty("username") String username,
                @JsonProperty("salt")     String salt,
                @JsonProperty("passHash") String passHash,
                @JsonProperty("token")    String token) {
        this.id = id; this.username = username; this.salt = salt; this.passHash = passHash; this.token = token;
    }

    public String getId()       { return id; }
    public String getUsername() { return username; }
    public String getSalt()     { return salt; }
    public String getPassHash() { return passHash; }
    public String getToken()    { return token; }

    public UserPlaceState getUserPlaceState()            { return userPlaceState; }
    public void setUserPlaceState(UserPlaceState state)  { this.userPlaceState = state; }
}
