package com.duaphine.blogger.services.impl;

import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.services.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final List<Post> posts;

    public PostServiceImpl() {
        posts = new ArrayList<>();
        posts.add(new Post());
        posts.add(new Post());
        posts.add(new Post());
    }

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) {
        return posts.stream().filter(post ->post.getCategory().getId().equals(categoryId)).collect(Collectors.toList());
    }

    @Override
    public List<Post> getAll() {
        return posts;
    }

    @Override
    public Post getById(UUID id) {
        return posts.stream().filter(post ->post.getCategory().getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Post create(String title, String content, UUID categoryId) {
        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedDate(LocalDateTime.now());
        Category category = new Category();
        category.setId(categoryId);
        category.setName("new name");
        post.setCategory(category);
        return post;
    }

    @Override
    public Post update(UUID id, String title, String content) {
        Post post = posts.stream().filter(c->id.equals(c.getId())).findFirst().orElse(null);
        if(post != null){
            post.setTitle(title);
            post.setContent(content);
        }
        return post;
    }

    @Override
    public boolean deleteById(UUID id) {
        return true;
    }
}
