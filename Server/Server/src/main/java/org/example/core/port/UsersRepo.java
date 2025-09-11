package org.example.core.port;

import org.example.core.domain.User;

public interface UsersRepo {
    User create(String username, String salt, String passHash); // null if exists
    User byUsername(String username);
    User byId(String id);
}
