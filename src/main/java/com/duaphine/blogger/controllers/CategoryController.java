package com.duaphine.blogger.controllers;

import com.duaphine.blogger.dto.CategoryRequest;
import com.duaphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.NoCategoryNameContainsStringException;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//Todo : changer all methodes return ResponseEntity
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
      summary = "Retrieve all categories or search by name fragment",
      description = "Returns all categories if no name is provided. If a name parameter is provided, returns categories that match the name string.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories (or (category) categories by name fragment)"),
      @ApiResponse(responseCode = "404", description = "No categories found matching the search criteria"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<List<Category>> getAll(
      @Parameter(description = "Optional search string to filter categories by name fragment. If omitted, all categories are returned.")
      @RequestParam(required = false) String name) throws NoCategoryNameContainsStringException {
    List<Category> categories =
        name == null || name.isBlank() ? service.getAll() : service.getByNameFragment(name);
    return ResponseEntity.ok(categories);
  }

  /*● Retrieve a category by id*/
  @GetMapping("{id}")
  @Operation(
      summary = "Retrieve a category by ID",
      description = "Returns a single category identified by its unique identifier (ID).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the category"),
      @ApiResponse(responseCode = "404", description = "Category not found with the provided ID"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })

  public ResponseEntity<Category> getById(
      @Parameter(description = "id of categories") @PathVariable UUID id)
      throws CategoryNotFoundByIdException {
    //test#01 : get with valid id --> category
    //test#02 : get with  invalid id -> Exception
    Category category = service.getById(id);
    return ResponseEntity.ok(category);
  }


  @PostMapping
  @Operation(
      summary = "Create a new category",
      description = "Creates a new category with the specified name. Returns the created category.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Category successfully created"),
      @ApiResponse(responseCode = "400", description = "Bad request, possibly due to invalid name or name already exists"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Category> create(
      @Parameter(description = "Name of the new category") @RequestBody CategoryRequest request)
      throws CategoryNameAlreadyExistsException {
    Category category = service.create(request.getName());
    return ResponseEntity
        .created(URI.create("/v1/categories" + category.getId()))
        .body(category);///v1/categories/{categoryId}
  }

  @PutMapping("{id}")
  @Operation(
      summary = "Update the name of a category",
      description = "Updates the name of the specified category by its ID. Returns the updated category.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category name successfully updated"),
      @ApiResponse(responseCode = "404", description = "Category not found with the provided ID"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Category> updateName(
      @Parameter(description = "The unique identifier (UUID) of the category to update") @PathVariable UUID id,
      @Parameter(description = "New name for the category") @RequestBody CategoryRequest request)
      throws CategoryNotFoundByIdException {
    return ResponseEntity.ok(service.updateName(id, request.getName()));
  }

  @DeleteMapping("{id}")
  @Operation(
      summary = "Delete a category",
      description = "Deletes the category identified by its ID. Returns true if the operation was successful.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Category successfully deleted"),
      @ApiResponse(responseCode = "404", description = "Category not found with the provided ID"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Boolean> deleteById(
      @Parameter(description = "The unique identifier (UUID) of the category to delete") @PathVariable UUID id)
      throws CategoryNotFoundByIdException {
    boolean deleteResulat = service.deleteById(id);
    if (!deleteResulat) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }

}
