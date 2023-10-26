package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostDto2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController

@Tag(
		name = "CRUD REST APIs for Post Resourse")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    @SecurityRequirement(
    		name="Bear Authentication")
    @Operation(
    		summary = "create post REST API",
    		description = "Create Post REST API is uses to save post data into database")
    @ApiResponse(
    		responseCode = "201",
    		description = "HTTP Status 201 CREATED")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts rest api
    @GetMapping("/api/v1/posts")
    @Operation(
    		summary = "Get All post REST API",
    		description = "Get All Post REST API is usesed to get post from the database")
    @ApiResponse(
    		responseCode = "200",
    		description = "HTTP Status 200 CREATED")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // get post by id
    @GetMapping("/api/v1/posts/{id}")
//    @GetMapping(value="/api/posts/{id}",params="version=1")
    @Operation(
    		summary = "get post REST API",
    		description = "get Post REST API is usesed to get post from the database")
    @ApiResponse(
    		responseCode = "200",
    		description = "HTTP Status 200 CREATED")
    public ResponseEntity<PostDto> getPostByIdv1(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    
 // get post by id
    @GetMapping("/api/v2/posts/{id}")
//    @GetMapping(value="/api/posts/{id}",params="version=2")
    @Operation(
    		summary = "get post REST API",
    		description = "get Post REST API is usesed to get post from the database")
    @ApiResponse(
    		responseCode = "200",
    		description = "HTTP Status 200 CREATED")
    public ResponseEntity<PostDto2> getPostByIdV2(@PathVariable(name = "id") long id){
    	
    	PostDto postDto=postService.getPostById(id);
    	PostDto2 postDto2=new PostDto2();
    	postDto2.setId(postDto.getId());
    	postDto2.setTitle(postDto.getTitle());
    	postDto2.setDescription(postDto.getDescription());
    	postDto2.setCategoryId(postDto.getCategoryId());
    	List<String > tags=new ArrayList<>();
    	tags.add("java");
    	tags.add("Spring boot");
    	tags.add("AWS");
    	postDto2.setTags(tags);
    	return ResponseEntity.ok(postDto2);
    }

    // update post by id rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    @SecurityRequirement(
    		name="Bear Authentication")
    @Operation(
    		summary = "Update post REST API",
    		description = "Update Post REST API is usesed to Update post in the database")
    @ApiResponse(
    		responseCode = "200",
    		description = "HTTP Status 200 CREATED")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){

       PostDto postResponse = postService.updatePost(postDto, id);

       return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // delete post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    @SecurityRequirement(
    		name="Bear Authentication")
    @Operation(
    		summary = "Delete post REST API",
    		description = "Delete Post REST API is usesed to Delete specific post from the database")
    @ApiResponse(
    		responseCode = "200",
    		description = "HTTP Status 200 CREATED")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping("/api/v1/posts/category/{id}")
    @Operation(
    		summary = "get posts  REST API",
    		description = "get Post REST API is usesed to get posts from the database by category")
    @ApiResponse(
    		responseCode = "200",
    		description = "HTTP Status 200 CREATED")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId)
    {
    	List<PostDto> postDtos=postService.getPostsByCategory(categoryId);
    	
    	return ResponseEntity.ok(postDtos);
    }
}
