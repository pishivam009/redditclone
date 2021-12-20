package com.piyush.springboot.reditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piyush.springboot.reditclone.dto.CommentsDto;
import com.piyush.springboot.reditclone.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments/")
public class CommentsController {

	private final CommentService commentService;
	
	@PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.createComment(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
}
