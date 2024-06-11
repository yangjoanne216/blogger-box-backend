package com.duaphine.blogger.services.impl;

import com.duaphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.NoCategoryNameContainsStringException;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.repository.CategoryRepository;
import com.duaphine.blogger.services.CategoryService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository repository;

  public CategoryServiceImpl(CategoryRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Category> getAll() {
    return repository.findAll();
  }

  @Override
  public Category getById(UUID id) throws CategoryNotFoundByIdException {
    return repository.findById(id).orElseThrow(() -> new CategoryNotFoundByIdException(id));
  }

  @Override
  public Category create(String name) throws CategoryNameAlreadyExistsException {
    Category category = new Category(name);
    if (repository.existsByName(name)) {
      throw new CategoryNameAlreadyExistsException(name);
    }
    return repository.save(category);
  }

  @Override
  public Category updateName(UUID id, String newName) throws CategoryNotFoundByIdException {
    Category category = repository.findById(id)
        .orElseThrow(() -> new CategoryNotFoundByIdException(id));
    category.setName(newName);
    return repository.save(category);
  }

  @Override
  public boolean deleteById(UUID id) throws CategoryNotFoundByIdException {
    if (!repository.existsById(id)) {
      throw new CategoryNotFoundByIdException(id);
    }
    repository.deleteById(id);
    return true;
  }

  public List<Category> getByNameFragment(String nameFragment)
      throws NoCategoryNameContainsStringException {
    Optional<List<Category>> categories = repository.findAllByNameFragment(nameFragment);
    if (categories.isEmpty() || categories.get().isEmpty()) {
      throw new NoCategoryNameContainsStringException(nameFragment);
    }
    return categories.get();
  }
}
