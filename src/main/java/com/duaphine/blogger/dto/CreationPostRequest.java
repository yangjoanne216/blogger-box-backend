package com.duaphine.blogger.dto;

import java.util.UUID;

public class CreationPostRequest {
    private UUID categoryId;
    private String title;
    private String content;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public CreationPostRequest() {
    }

    public CreationPostRequest(UUID categoryId, String title, String content) {
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
    }
}
