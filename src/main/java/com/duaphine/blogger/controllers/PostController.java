package com.duaphine.blogger.controllers;

import com.duaphine.blogger.dto.PostRequest;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts") /*Retrive all posts*/
public class PostController {
    private final PostService service;
    public PostController(PostService service) {
        this.service = service;
    }
    @GetMapping("{categoryId}")
    @Operation(
            summary = "Retrieve all posts for a category",
            description = "Returns all posts associated with the specified category ID."
    )
    public List<Post> getAllByCategoryId(@PathVariable UUID categoryId) {
        return service.getAllByCategoryId(categoryId) ;
    }

    @GetMapping
    public List<Post> getAll(){
        return service.getAll();
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Retrieve a post by id",
            description = ""
    )
    public Post getById(@Parameter(description = "id of post")@PathVariable UUID id ){
        return service.getById(id);
    }

    @PostMapping
    @Operation(
            summary = "creat a new post",
            description = ""
    )
    public  Post  create(@RequestBody PostRequest request){
        return service.create(request.getTitle(),request.getContent(),request.getCategory().getId());
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Update an existing post",
            description = ""
    )
    public  Post update(@Parameter(description = "id of post") UUID id,
                                   @RequestBody PostRequest request) {
        return service.update(id,request.getTitle(),request.getContent());
    }

    @DeleteMapping ("{id}")
    @Operation(
            summary = "Delete an existing post",
            description = ""
    )
    public boolean deleteById(@PathVariable UUID id){

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
