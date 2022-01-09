package com.piyush.springboot.reditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piyush.springboot.reditclone.model.Post;
import com.piyush.springboot.reditclone.model.User;
import com.piyush.springboot.reditclone.model.Vote;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, Long>{

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
