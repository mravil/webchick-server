
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.model;

/**
 *
 * @author Administrator
 */
public enum UserRole {
    /**
     * the role is unknown , role value is -1
     */
    UNKNOWN(-1, "UNKNOWN"),
    /**
     * the role is guest , role value is 0
     */
    GUEST(0, "GUEST"),
    /**
     * the role is regular user , role value is 1
     */
    USER(1, "USER"),
    /**
     * the role is distributor , role value is 2
     */
    DISTRIBUTOR(2, "DISTRIBUTOR"),
    /**
     * the role is administrator , role value is 3a
     */
    ADMIN(3, "ADMIN");

    private int value;

    private String text;

    private UserRole(int role, String text) {
        this.value = role;
        this.text = text;
    }

    /**
     * Return role value
     * @return the role value
     */
    public int getValue() {
        return value;
    }

    /**
     * Return role text
     * @return the role text
     */
    public String getText() {
        return text;
    }

    public static UserRole get(int role) {
        switch (role) {
        case 0 :
            return UserRole.GUEST;

        case 1 :
            return UserRole.USER;

        case 2 :
            return UserRole.DISTRIBUTOR;

        case 3 :
            return UserRole.ADMIN;

        default :
            return UserRole.UNKNOWN;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
