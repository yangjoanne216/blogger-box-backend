package com.duaphine.blogger.controllers;

import com.duaphine.blogger.dto.CategoryRequest;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")/*Retrieve all categories*/
public class CategoryController {

    private  final List<Category> categories;

    public CategoryController() {
      categories = new ArrayList<>();
      categories.add(new Category(UUID.randomUUID(),"My first category"));
      categories.add(new Category(UUID.randomUUID(),"My second category"));
      categories.add(new Category(UUID.randomUUID(),"My thied category"));
    }
@GetMapping
public List<Category> getAll(){
        return categories;
}

    /*● Retrieve a category by id*/
@GetMapping("{id}")
@Operation(
        summary = "Retrieve a category by id",
        description = ""
)
public Category getById( @Parameter(description = "id of categories")@PathVariable UUID id ){
    return categories.stream().filter(category -> category.getId().equals(id)).findFirst().orElse(null);
}


@PostMapping
@Operation(
        summary = "creat a new category",
        description = ""
)
public  Category  create(@RequestBody CategoryRequest request){
   Category category = new Category();
   category.setName(request.getName());
   category.setId(UUID.randomUUID());
   categories.add(category);
   return category;
}

@PutMapping("{id}")
@Operation(
        summary = "Update the name of a category",
        description = ""
)
public  Category  update(@PathVariable UUID id,
                               @RequestBody CategoryRequest request) {
    categories.stream().filter(c->c.getId().equals(id)).findFirst().ifPresent(c->c.setName(request.getName()));
    return categories.get(0);
}

@DeleteMapping ("{id}")
@Operation(
        summary = "Delete an existing category",
        description = ""
)
public boolean delect (@PathVariable UUID id){
    return true;
}

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


}
