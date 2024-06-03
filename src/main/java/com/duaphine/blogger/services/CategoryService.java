package com.duaphine.blogger.services;

import com.duaphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.NoCategoryNameContainsStringException;
import com.duaphine.blogger.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAll();
    Category getById(UUID id) throws CategoryNotFoundByIdException;
    Category create(String name) throws CategoryNameAlreadyExistsException;
    Category updateName(UUID id, String name) throws CategoryNotFoundByIdException;
    boolean deleteById(UUID id) throws CategoryNotFoundByIdException;
    List<Category> getByNameFragment(String name) throws NoCategoryNameContainsStringException;
}
