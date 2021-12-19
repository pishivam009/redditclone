package com.piyush.springboot.reditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piyush.springboot.reditclone.model.Subreddit;
import com.piyush.springboot.reditclone.model.Post;
import com.piyush.springboot.reditclone.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}

