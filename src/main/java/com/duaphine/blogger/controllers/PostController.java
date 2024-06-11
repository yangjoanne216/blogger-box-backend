package com.duaphine.blogger.controllers;

import com.duaphine.blogger.dto.PostRequest;
import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByNameException;
import com.duaphine.blogger.exceptions.NoPostTitleOrContentContainsStringException;
import com.duaphine.blogger.exceptions.PostNotFoundByIdException;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.services.PostService;
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

@RestController
@Tag(name = "Post Controller API",
    description = "API to manage posts, including creating, retrieving, updating, and deleting posts."
)
@RequestMapping("/v1/posts") /*Retrive all posts*/
public class PostController {

  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  @GetMapping
  @Operation(
      summary = "Retrieve all posts (you can also search a post by title or content)",
      description = "Returns all posts if no search parameter is provided. If a search parameter is provided, it returns posts that contain the search string in their title or content."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
      @ApiResponse(responseCode = "404", description = "No posts found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<List<Post>> getAll(
      @Parameter(description = "Optional search string to filter posts by title or content. If omitted, all posts are returned.")
      @RequestParam(required = false) String search)
      throws NoPostTitleOrContentContainsStringException {
    List<Post> posts = search == null || search.isBlank() ? service.getAll()
        : service.getAllByTitleOrContent(search);
    return ResponseEntity.ok(posts);
  }

  @GetMapping("by-category/{categoryId}")
  @Operation(
      summary = "Retrieve all posts for a category",
      description = "Returns all posts associated with the specified category ID."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
      @ApiResponse(responseCode = "404", description = "Category not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<List<Post>> getAllByCategoryId(
      @Parameter(description = "The unique identifier (UUID) of the category") @PathVariable UUID categoryId)
      throws CategoryNotFoundByIdException {
    List<Post> posts = service.getAllByCategoryId(categoryId);
    return ResponseEntity.ok(posts);
  }

  @GetMapping("{id}")
  @Operation(
      summary = "Retrieve a post by id",
      description = "Returns a single post matched by the unique identifier (ID). Useful for retrieving detailed information about a specific post."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved post"),
      @ApiResponse(responseCode = "404", description = "Post not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Post> getById(@Parameter(description = "id of post") @PathVariable UUID id)
      throws PostNotFoundByIdException {
    Post post = service.getById(id);
    return ResponseEntity.ok(post);
  }

  @PostMapping
  @Operation(
      summary = "Creat a new post",
      description = "Creates a new post with the given title, content, and category ID provided in the request body. Returns the created post."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created post"),
      @ApiResponse(responseCode = "404", description = "category not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Post> create(
      @Parameter(description = "a post with title and content and categoryID") @RequestBody PostRequest request)
      throws CategoryNotFoundByIdException, CategoryNotFoundByNameException {
    Post post = service.create(request.getTitle(), request.getContent(),
        request.getCategoryName());
    return ResponseEntity.created(URI.create("v1/posts/" + post.getId())).body(post);
  }

  @PutMapping("{id}")
  @Operation(
      summary = "Update an existing post",
      description = "Updates the title and content of an existing post identified by its ID. Returns the updated post."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated post"),
      @ApiResponse(responseCode = "404", description = "Post not found or category not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Post> update(@Parameter(description = "id of post") @PathVariable UUID id,
      @Parameter(description = "a post with title and content and category name") @RequestBody PostRequest request)
      throws PostNotFoundByIdException, CategoryNotFoundByNameException {
    Post updatePost = service.update(id, request.getTitle(), request.getContent(),
        request.getCategoryName());
    return ResponseEntity.ok(updatePost);
  }

  @DeleteMapping("{id}")
  @Operation(
      summary = "Delete an existing post",
      description = "Deletes the post identified by its ID. Returns a boolean indicating whether the deletion was successful."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Successfully deleted post"),
      @ApiResponse(responseCode = "404", description = "Post not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<Boolean> deleteById(
      @Parameter(description = "id of post") @PathVariable UUID id)
      throws PostNotFoundByIdException {
    Boolean result = service.deleteById(id);
    if (result) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }


  @GetMapping("sorted")
  @Operation(
      summary = "Retrieve all posts ordered by creation date",
      description = "Returns all posts sorted by their creation dates in descending order."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
      @ApiResponse(responseCode = "404", description = "No posts found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public ResponseEntity<List<Post>> retrievePostsOrderedByCreatedDate() {
    List<Post> posts = service.findAllByOrderByCreatedDateDesc();
    return ResponseEntity.ok(posts);
  }

}
