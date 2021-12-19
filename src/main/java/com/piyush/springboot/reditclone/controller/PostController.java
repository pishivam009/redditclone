package com.piyush.springboot.reditclone.controller;


import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piyush.springboot.reditclone.dto.PostRequest;
import com.piyush.springboot.reditclone.dto.PostResponse;
import com.piyush.springboot.reditclone.service.PostService;
import com.piyush.springboot.reditclone.service.SubredditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

	private final PostService postService;
	
	   @PostMapping
	    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
	        postService.save(postRequest);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    }

	    @GetMapping
	    public ResponseEntity<List<PostResponse>> getAllPosts() {
	        return status(HttpStatus.OK).body(postService.getAllPosts());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
	        return status(HttpStatus.OK).body(postService.getPost(id));
	    }

	    @GetMapping("by-subreddit/{id}")
	    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
	        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	    }

	    @GetMapping("by-user/{name}")
	    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
	        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
	    }


}
