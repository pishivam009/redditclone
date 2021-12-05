package com.piyush.springboot.reditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piyush.springboot.reditclone.model.Comment;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long>{

}
