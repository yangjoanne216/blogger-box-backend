package com.duaphine.blogger.controllers;

import com.duaphine.blogger.dto.PostRequest;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts") /*Retrive all posts*/
public class PostController {
    /*
● Create a new post
● Update an existing post
● Delete an existing post
● Retrieve all posts ordered by creation date
● Retrieve all posts per a category*/
    @GetMapping
    public String getAll(){
        return "all posts";
    }
    @PostMapping
    @Operation(
            summary = "creat a new post",
            description = ""
    )
    public  Post  create(@RequestBody PostRequest request){
        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedDate(new Date());
        Category category = new Category();
        category.setId(request.getId());
        category.setName("new name");
        post.setCategory(category);
        return post;
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Update an existing post",
            description = ""
    )
    public  String  update(@Parameter(description = "id of post") int id,
                                   @RequestBody PostRequest request) {
        return "update post '%d' with title '%s', content '%s' category id '%d' and date '%tY-%tm-%td'".formatted(id, request.getTitle(),request.getContent(),request.getCategory(),request.getCreatedDate());
    }

    @DeleteMapping ("{id}")
    @Operation(
            summary = "Delete an existing post",
            description = ""
    )
    public String delete(@PathVariable Integer id){
        return "Delete post '%s'".formatted(id);
    }

    @GetMapping("sorted")
    @Operation(
            summary = "Retrieve all posts ordered by creation date",
            description = "Returns all posts sorted by their creation dates in descending order."
    )
    public String retrievePostsOrderedByCreationDate() {

        return "postService.findAllSortedByCreatedDateDesc()";
    }
    @GetMapping("{categoryId}")
    @Operation(
            summary = "Retrieve all posts for a category",
            description = "Returns all posts associated with the specified category ID."
    )
    public String retrievePostsByCategory(@PathVariable UUID categoryId) {
        //Todo List<Post> posts = postService.findAllByCategoryId(categoryId);
        return "postService.findAllByCategoryId(categoryId) :" + categoryId ;
    }






}
