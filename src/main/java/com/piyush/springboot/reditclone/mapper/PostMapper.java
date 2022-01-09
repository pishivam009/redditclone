package com.piyush.springboot.reditclone.mapper;

import org.mapstruct.Mapper;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.piyush.springboot.reditclone.dto.PostRequest;
import com.piyush.springboot.reditclone.dto.PostResponse;
import com.piyush.springboot.reditclone.model.Post;
import com.piyush.springboot.reditclone.model.Subreddit;
import com.piyush.springboot.reditclone.model.User;
import com.piyush.springboot.reditclone.repository.CommentRepository;
import com.piyush.springboot.reditclone.repository.VoteRepository;
import com.piyush.springboot.reditclone.service.AuthService;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0") //by default zero while saving
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
