package com.duaphine.blogger.controllers;

import com.duaphine.blogger.models.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")/*Retrieve all categories*/
public class CategoryController {

    private  final List<Category> temporaryCategories;

    public CategoryController() {
        temporaryCategories = new ArrayList<>();
        temporaryCategories.add(new Category(UUID.randomUUID(),"My first category"));
        temporaryCategories.add(new Category(UUID.randomUUID(),"My second category"));
        temporaryCategories.add(new Category(UUID.randomUUID(),"My thied category"));
    }
@GetMapping
public String retriveAllCategories(){
        return "all categories";
}

    /*● Retrieve a category by id*/
@GetMapping("/{id}")
@Operation(
        summary = "Retrieve a category by id",
        description = ""
)
public String categoryById( @Parameter(description = "id of categories")@PathVariable UUID id ){
    return
            "This is category " + id;
}


@PostMapping
@Operation(
        summary = "creat a new category",
        description = ""
)
public  String  createCategory(@RequestBody Category category){
    return "Create new category with  id '%d' and name '%s'".formatted(category.getId(),category.getName());
}

@PutMapping("/{id}")
@Operation(
        summary = "Update the name of a category",
        description = ""
)
public  String  updateCategory(@Parameter(description = "id of categories") int id,
                               @RequestBody Category category) {
    return "update category '%d' with name '%s'".formatted(category.getId(), category.getName());
}


@DeleteMapping ("/{id}")
@Operation(
        summary = "Delete an existing category",
        description = ""
)

public String DelectCategory(@PathVariable Integer id){
    return "Delete element '%s'".formatted(id);
}


}
