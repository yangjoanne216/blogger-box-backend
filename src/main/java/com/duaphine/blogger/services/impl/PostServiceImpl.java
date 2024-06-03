package com.duaphine.blogger.services.impl;

import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.NoPostTitleOrContentContainsStringException;
import com.duaphine.blogger.exceptions.PostNotFoundByIdException;
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

    private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository repository,  CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Post> getAllByCategoryId(UUID categoryId) throws CategoryNotFoundByIdException {
        return repository.findAllByCategoryId(categoryId).orElseThrow(()->new CategoryNotFoundByIdException(categoryId));
    }

    @Override
    public List<Post> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Post> getAllByTitleOrContent(String search) throws NoPostTitleOrContentContainsStringException {

        return repository.findAllByTitleOrContent(search).orElseThrow(()->new NoPostTitleOrContentContainsStringException(search));
    }

    @Override
    public Post getById(UUID id) throws PostNotFoundByIdException {
       return repository.findById(id).orElseThrow(()->new PostNotFoundByIdException(id));

    }

    @Override
    public Post create(String title, String content, UUID categoryId) throws CategoryNotFoundByIdException {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundByIdException(categoryId));
        post.setCategory(category);
        return repository.save(post);
    }

    @Override
    public Post update(UUID id, String title, String content) throws PostNotFoundByIdException {
        Post post = repository.findById(id).orElseThrow(()->new PostNotFoundByIdException(id));
        post.setContent(content);
        post.setTitle(title);
        return repository.save(post);
    }

    @Override
    public boolean deleteById(UUID id) throws PostNotFoundByIdException {
        if(!repository.existsById(id)){
            throw new PostNotFoundByIdException(id);
        }
        repository.deleteById(id);
        return true;
    }

    public List<Post> findAllByOrderByCreatedDateDesc() {
        return repository.findAllByOrderByCreatedDateDesc();
    }
}
