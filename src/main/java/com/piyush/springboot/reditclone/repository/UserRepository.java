package com.piyush.springboot.reditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.piyush.springboot.reditclone.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

}
