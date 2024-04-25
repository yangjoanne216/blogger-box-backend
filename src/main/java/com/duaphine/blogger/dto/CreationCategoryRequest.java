package com.duaphine.blogger.dto;

public class CreationCategoryRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreationCategoryRequest() {
    }

    public CreationCategoryRequest(String name) {
        this.name = name;
    }
}
