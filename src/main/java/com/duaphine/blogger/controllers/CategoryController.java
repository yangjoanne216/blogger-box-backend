package com.duaphine.blogger.controllers;

import com.duaphine.blogger.dto.CategoryRequest;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")/*Retrieve all categories*/
@Tag(name = "Category Controller API", description = "API to manage categories, including creating, retrieving, updating, and deleting categories.")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

@GetMapping
@Operation(
        summary = "Retrieve all categories or search by name",
        description = "Returns all categories if no name is provided. If a name parameter is provided, returns categories that match the name string.")
public List<Category> getAll(@Parameter(description = "Optional search string to filter categories by name. If omitted, all categories are returned.")@RequestParam(required = false) String name){
        return name==null|| name.isBlank()?service.getAll():service.getAllByName(name);
}

    /*● Retrieve a category by id*/
@GetMapping("{id}")
@Operation(
        summary = "Retrieve a category by ID",
        description = "Returns a single category identified by its unique identifier (ID).")
public Category getById( @Parameter(description = "id of categories")@PathVariable UUID id ){
    return service.getById(id);
}


@PostMapping
@Operation(
        summary = "Create a new category",
        description = "Creates a new category with the specified name. Returns the created category.")
public  Category  create(@Parameter(description = "Name of the new category")@RequestBody String name){
  return service.create(name);
}

@PutMapping("{id}")
@Operation(
        summary = "Update the name of a category",
        description = "Updates the name of the specified category by its ID. Returns the updated category.")
public  Category  updateName(@Parameter(description = "The unique identifier (UUID) of the category to update")@PathVariable UUID id,
                             @Parameter(description = "New name for the category")@RequestBody String name) {
return service.updateName(id,name);
}

@DeleteMapping ("{id}")
@Operation(
        summary = "Delete a category",
        description = "Deletes the category identified by its ID. Returns true if the operation was successful.")
public boolean deleteById (@Parameter(description = "The unique identifier (UUID) of the category to delete")@PathVariable UUID id){
    return service.deleteById(id);
}

/*
@GetMapping("{id}/posts")
@Operation(
        summary = "get all categories by id",
        description = ""
)
public List<Post> getAllPostsByCategoryId(@PathVariable UUID categoryId)
   {
        List<Post> posts = new ArrayList<>();
        return posts;
   }
 */

}
