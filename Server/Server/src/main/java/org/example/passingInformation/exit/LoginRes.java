package org.example.passingInformation.exit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class LoginRes {
    private final String userName;
    private final String id;

    @JsonCreator
    public LoginRes(@JsonProperty("userName") String userName , @JsonProperty("id") String id) {
        this.userName = userName;
        this.id = id;
    }

    public String getUserName() { return userName; }

    public String getId() {
        return id;
    }
}
