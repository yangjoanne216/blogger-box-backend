package com.duaphine.blogger.models;

import java.util.UUID;

public class Category {
    private UUID id;
    private String name;

    public Category(UUID randomUUID, String my_first_category) {
    }

    public Category() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
