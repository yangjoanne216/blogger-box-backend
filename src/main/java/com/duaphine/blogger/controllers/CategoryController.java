package com.duaphine.blogger.controllers;

import com.duaphine.blogger.dto.CategoryRequest;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")/*Retrieve all categories*/
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }
@GetMapping
public List<Category> getAll(){
        return service.getAll();
}

    /*● Retrieve a category by id*/
@GetMapping("{id}")
@Operation(
        summary = "Retrieve a category by id",
        description = ""
)
public Category getById( @Parameter(description = "id of categories")@PathVariable UUID id ){
    return service.getById(id);
}


@PostMapping
@Operation(
        summary = "creat a new category",
        description = ""
)
public  Category  create(@RequestBody String name){
  return service.create(name);
}

@PutMapping("{id}")
@Operation(
        summary = "Update the name of a category",
        description = ""
)
public  Category  updateName(@PathVariable UUID id,
                               @RequestBody String name) {
return service.updateName(id,name);
}

@DeleteMapping ("{id}")
@Operation(
        summary = "Delete an existing category",
        description = ""
)

public boolean deleteById (@PathVariable UUID id){
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
