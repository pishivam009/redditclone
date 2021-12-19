package com.piyush.springboot.reditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piyush.springboot.reditclone.model.Subreddit;

@Repository
public interface SubredditRepository  extends JpaRepository<Subreddit, Long>{

	Optional<Subreddit> findByName(String subredditName);

}
