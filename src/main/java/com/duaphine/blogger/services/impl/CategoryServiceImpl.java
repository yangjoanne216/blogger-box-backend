package com.duaphine.blogger.services.impl;

import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final List<Category>  categories;

    public CategoryServiceImpl() {
        categories = new ArrayList<>();
        categories.add(new Category(UUID.randomUUID(),"My first category"));
        categories.add(new Category(UUID.randomUUID(),"My second category"));
        categories.add(new Category(UUID.randomUUID(),"My thied category"));
    }


    @Override
    public List<Category> getAll() {
        return categories;
    }

    @Override
    public Category getById(UUID id) {
        return categories.stream().filter(category -> category.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Category create(String name) {
        Category category = new Category();
        category.setName(name);
        category.setId(UUID.randomUUID());
        categories.add(category);
        return category;
    }

    @Override
    public Category updateName(UUID id, String newName) {
        Category category = categories.stream().filter(c->id.equals(c.getId())).findFirst().orElse(null);
        if(category != null){
            category.setName(newName);
        }
        return category;
    }

    @Override
    public boolean deleteById(UUID id) {
        return true;
    }
}
