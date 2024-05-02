package com.duaphine.blogger.services.impl;

import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.repository.CategoryRepository;
import com.duaphine.blogger.repository.PostRepository;
import com.duaphine.blogger.services.CategoryService;
import com.duaphine.blogger.services.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository repository;
    private final CategoryService categoryService;

    public PostServiceImpl(PostRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) {
        return repository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Post> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Post> getAllByTitleOrContent(String search) {
        return repository.findAllByTitleOrContent(search);
    }

    @Override
    public Post getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Post create(String title, String content, UUID categoryId) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        Category category = categoryService.getById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }

        post.setCategory(category);
        return repository.save(post);
    }

    @Override
    public Post update(UUID id, String title, String content) {
        Post post = getById(id);
        if(post == null){
            return null;
        }
       post.setContent(content);
        post.setTitle(title);
        return repository.save(post);
    }

    @Override
    public boolean deleteById(UUID id) {
        repository.deleteById(id);
        return true;
    }
}
