package com.kenzie.appserver.service.model;

public class RSVP {
    private final String id;
    private final String name;

    public RSVP(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
