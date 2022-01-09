package com.piyush.springboot.reditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piyush.springboot.reditclone.model.Comment;
import com.piyush.springboot.reditclone.model.Post;
import com.piyush.springboot.reditclone.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPost(Post post);

	List<Comment> findAllByUser(User user);
}
