package com.huda.money_track.entity;

public enum Role {
    ADMIN("ADMIN"), MEMBER("MEMBER");

    Role(String name) {
    }
    
    public String getName() {
        return this.name();
    }
}
