package com.duaphine.blogger.services.impl;

import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.repository.CategoryRepository;
import com.duaphine.blogger.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final List<Category>  categories;
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
        categories = new ArrayList<>();
        categories.add(new Category(UUID.randomUUID(),"My first category"));
        categories.add(new Category(UUID.randomUUID(),"My second category"));
        categories.add(new Category(UUID.randomUUID(),"My thied category"));
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Category create(String name) {
        Category category = new Category(name);
        return repository.save(category);
    }

    @Override
    public Category updateName(UUID id, String newName) {
        Category category = getById(id);
        if(category==null){
            return null;
        }
        category.setName(newName);
        return repository.save(category);
    }

    @Override
    public boolean deleteById(UUID id) {
        repository.deleteById(id);
        return true;
    }
}
