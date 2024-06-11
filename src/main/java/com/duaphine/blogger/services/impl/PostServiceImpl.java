package com.duaphine.blogger.services.impl;

import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByNameException;
import com.duaphine.blogger.exceptions.NoPostTitleOrContentContainsStringException;
import com.duaphine.blogger.exceptions.PostNotFoundByIdException;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.repository.CategoryRepository;
import com.duaphine.blogger.repository.PostRepository;
import com.duaphine.blogger.services.PostService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {


  private final PostRepository repository;

  private final CategoryRepository categoryRepository;

  public PostServiceImpl(PostRepository repository, CategoryRepository categoryRepository) {
    this.repository = repository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<Post> getAllByCategoryId(UUID categoryId) throws CategoryNotFoundByIdException {
    if (!categoryRepository.existsById(categoryId)) {
      throw new CategoryNotFoundByIdException(categoryId);
    }
    return repository.findAllByCategoryId(categoryId)
        .orElseThrow(() -> new CategoryNotFoundByIdException(categoryId));
  }

  @Override
  public List<Post> getAll() {
    return repository.findAll();
  }

  @Override
  public List<Post> getAllByTitleOrContent(String search)
      throws NoPostTitleOrContentContainsStringException {
    Optional<List<Post>> optionalPosts = repository.findAllByTitleOrContent(search);
    if (optionalPosts.isPresent() && optionalPosts.get().isEmpty()) {
      throw new NoPostTitleOrContentContainsStringException(search);
    }
    return optionalPosts.orElseThrow(() -> new NoPostTitleOrContentContainsStringException(search));
  }

  @Override
  public Post getById(UUID id) throws PostNotFoundByIdException {
    return repository.findById(id).orElseThrow(() -> new PostNotFoundByIdException(id));

  }

  @Override
  public Post create(String title, String content, String categoryName)
      throws CategoryNotFoundByNameException {
    Post post = new Post();
    post.setTitle(title);
    post.setContent(content);
    Category category = categoryRepository.findByName(categoryName)
        .orElseThrow(() -> new CategoryNotFoundByNameException(categoryName));
    post.setCategory(category);
    post.setCreatedDate(LocalDateTime.now());
    return repository.save(post);
  }

  @Override
  public Post update(UUID id, String title, String content, String categoryName)
      throws PostNotFoundByIdException, CategoryNotFoundByNameException {
    Post post = repository.findById(id).orElseThrow(() -> new PostNotFoundByIdException(id));
    Category category = categoryRepository.findByName(categoryName)
        .orElseThrow(() -> new CategoryNotFoundByNameException(categoryName));
    post.setContent(content);
    post.setTitle(title);
    post.setCategory(category);
    return repository.save(post);
  }

  @Override
  public boolean deleteById(UUID id) throws PostNotFoundByIdException {
    if (!repository.existsById(id)) {
      throw new PostNotFoundByIdException(id);
    }
    repository.deleteById(id);
    return true;
  }

  public List<Post> findAllByOrderByCreatedDateDesc() {
    return repository.findAllByOrderByCreatedDateDesc();
  }
}
