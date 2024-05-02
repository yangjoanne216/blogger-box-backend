package com.duaphine.blogger.controllers;

import com.duaphine.blogger.dto.PostRequest;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @GetMapping("by-category/{categoryId}")
    @Operation(
            summary = "Retrieve all posts for a category",
            description = "Returns all posts associated with the specified category ID."
    )
    public List<Post> getAllByCategoryId(@Parameter(description = "The unique identifier (UUID) of the category")@PathVariable UUID categoryId) {
        return service.getAllByCategoryId(categoryId) ;
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all posts for a category by title or content",
            description = "Returns all posts if no search parameter is provided. If a search parameter is provided, it returns posts that contain the search string in their title or content."
    )
    public List<Post> getAll(@Parameter(description = "Optional search string to filter posts by title or content. If omitted, all posts are returned.")@RequestParam(required = false) String search){
        if (search == null || search.isBlank()) {
            return service.getAll();
        } else {
            return service.getAllByTitleOrContent(search);
        }
    }


    @GetMapping("{id}")
    @Operation(
            summary = "Retrieve a post by id",
            description = "Returns a single post matched by the unique identifier (ID). Useful for retrieving detailed information about a specific post."
    )
    public Post getById(@Parameter(description = "id of post")@PathVariable UUID id ){
        return service.getById(id);
    }

    @PostMapping
    @Operation(
            summary = "Creat a new post",
            description = "Creates a new post with the given title, content, and category ID provided in the request body. Returns the created post."
    )
    public  Post  create(@Parameter(description = "a post with title and content and categoryID")@RequestBody PostRequest request){
        return service.create(request.getTitle(),request.getContent(),request.getCategory().getId());
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Update an existing post",
            description = "Updates the title and content of an existing post identified by its ID. Returns the updated post."
    )
    public  Post update(@Parameter(description = "id of post") UUID id,
                        @Parameter(description = "a post with title and content")@RequestBody PostRequest request) {
        return service.update(id,request.getTitle(),request.getContent());
    }

    @DeleteMapping ("{id}")
    @Operation(
            summary = "Delete an existing post",
            description = "Deletes the post identified by its ID. Returns a boolean indicating whether the deletion was successful."
    )
    public boolean deleteById(@Parameter(description = "id of post")@PathVariable UUID id){

        return service.deleteById(id);
    }
    
    /*
    @GetMapping("sorted")
    @Operation(
            summary = "Retrieve all posts ordered by creation date",
            description = "Returns all posts sorted by their creation dates in descending order."
    )
    public String retrievePostsOrderedByCreationDate() {

        return "postService.findAllSortedByCreatedDateDesc()";
    }*/

}
