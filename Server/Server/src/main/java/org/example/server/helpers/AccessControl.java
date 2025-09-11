package org.example.server.helpers;

import org.example.core.domain.Board;
import org.example.core.domain.User;

public final class AccessControl {
    public static boolean mayViewBoard(User u, Board b){ return b.getOwnerId().equals(u.getId()) || b.getMembers().contains(u.getId()); }
    public static boolean mayMutateBoard(User u, Board b){ return mayViewBoard(u,b); } // adjust per rules
}