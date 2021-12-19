package com.piyush.springboot.reditclone.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piyush.springboot.reditclone.dto.PostRequest;
import com.piyush.springboot.reditclone.dto.PostResponse;
import com.piyush.springboot.reditclone.exceptions.PostNotFoundException;
import com.piyush.springboot.reditclone.exceptions.SubredditNotFoundException;
import com.piyush.springboot.reditclone.mapper.PostMapper;
import com.piyush.springboot.reditclone.model.Post;
import com.piyush.springboot.reditclone.model.Subreddit;
import com.piyush.springboot.reditclone.repository.PostRepository;
import com.piyush.springboot.reditclone.repository.SubredditRepository;
import com.piyush.springboot.reditclone.repository.UserRepository;
import com.piyush.springboot.reditclone.model.User;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

	public final SubredditRepository subredditRepository;
	public final AuthService authService;
	public final PostMapper postMapper;
	public final PostRepository postRepository;
	public final UserRepository userRepository;
	
	
	public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

	 @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

	@Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

	 @Transactional(readOnly = true)
	    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
	        Subreddit subreddit = subredditRepository.findById(subredditId)
	                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
	        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
	        return posts.stream().map(postMapper::mapToDto).collect(toList());
	    }
	 
	 @Transactional(readOnly = true)
	    public List<PostResponse> getPostsByUsername(String username) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException(username));
	        return postRepository.findByUser(user)
	                .stream()
	                .map(postMapper::mapToDto)
	                .collect(toList());
	    }
	 
	 

}
