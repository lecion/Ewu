package com.yliec.ewu.api.entity.element;

/**
 * Created by Lecion on 12/23/15.
 */
public class AuthUser {
    String name;
    String password;

    public AuthUser(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
