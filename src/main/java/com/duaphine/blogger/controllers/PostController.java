package com.duaphine.blogger.controllers;

import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public String retriveAllPosts(){
        return "all posts";
    }
    @PostMapping
    @Operation(
            summary = "creat a new post",
            description = ""
    )
    public  String  createPost(@RequestBody Post post){
        return ("Create new category with  id '%d', title '%s', content '%s' " +
                "category id ''%d' and date '%tY-%tm-%td'").formatted(post.getId(),post.getTitle(),post.getContent(),post.getCategory_id(),post.getCreated_date());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing post",
            description = ""
    )
    public  String  updatePost(@Parameter(description = "id of post") int id,
                                   @RequestBody Post post) {
        return "update post '%d' with title '%s', content '%s' category id '%d' and date '%tY-%tm-%td'".formatted(id, post.getTitle(),post.getContent(),post.getCategory_id(),post.getCreated_date());
    }

    @DeleteMapping ("/{id}")
    @Operation(
            summary = "Delete an existing post",
            description = ""
    )

    public String DelectPost(@PathVariable Integer id){
        return "Delete post '%s'".formatted(id);
    }

    @GetMapping("/sorted")
    @Operation(
            summary = "Retrieve all posts ordered by creation date",
            description = "Returns all posts sorted by their creation dates in descending order."
    )
    public String retrievePostsOrderedByCreationDate() {

        return "postService.findAllSortedByCreatedDateDesc()";
    }
    @GetMapping("/{categoryId}")
    @Operation(
            summary = "Retrieve all posts for a category",
            description = "Returns all posts associated with the specified category ID."
    )
    public String retrievePostsByCategory(@PathVariable UUID categoryId) {
        //Todo List<Post> posts = postService.findAllByCategoryId(categoryId);
        return "postService.findAllByCategoryId(categoryId) :" + categoryId ;
    }






}
