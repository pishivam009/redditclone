package com.piyush.springboot.reditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.piyush.springboot.reditclone.dto.PostRequest;
import com.piyush.springboot.reditclone.dto.PostResponse;
import com.piyush.springboot.reditclone.model.Post;
import com.piyush.springboot.reditclone.model.Subreddit;
import com.piyush.springboot.reditclone.model.User;

@Mapper(componentModel = "spring")
public interface PostMapper {

	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "voteCount", constant = "0")
	@Mapping(target = "user", source = "user")
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

	@Mapping(target = "id", source = "postId")
	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source = "user.username")
	public abstract PostResponse mapToDto(Post post);

}
