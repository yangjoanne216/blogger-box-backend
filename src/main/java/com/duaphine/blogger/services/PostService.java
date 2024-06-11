package com.duaphine.blogger.services;

import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByNameException;
import com.duaphine.blogger.exceptions.NoPostTitleOrContentContainsStringException;
import com.duaphine.blogger.exceptions.PostNotFoundByIdException;
import com.duaphine.blogger.models.Post;
import java.util.List;
import java.util.UUID;

public interface PostService {

  List<Post> getAllByCategoryId(UUID categoryId) throws CategoryNotFoundByIdException;

  List<Post> getAll();

  List<Post> getAllByTitleOrContent(String search)
      throws NoPostTitleOrContentContainsStringException;

  Post getById(UUID id) throws PostNotFoundByIdException;

  Post create(String title, String content, String categoryName)
      throws CategoryNotFoundByIdException, CategoryNotFoundByNameException;

  Post update(UUID id, String title, String content, String categoryName)
      throws PostNotFoundByIdException, CategoryNotFoundByNameException;

  boolean deleteById(UUID id) throws PostNotFoundByIdException;

  List<Post> findAllByOrderByCreatedDateDesc();
}
