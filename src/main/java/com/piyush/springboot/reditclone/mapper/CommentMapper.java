package com.piyush.springboot.reditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.piyush.springboot.reditclone.dto.CommentsDto;
import com.piyush.springboot.reditclone.model.Comment;
import com.piyush.springboot.reditclone.model.Post;
import com.piyush.springboot.reditclone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	@Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
}
