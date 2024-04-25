package com.duaphine.blogger.dto;

import java.util.UUID;

public class UpdateCategoryRequest {
    private UUID id;
    private String name;


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

    public UpdateCategoryRequest() {
    }

    public UpdateCategoryRequest(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

}
