package com.piyush.springboot.reditclone.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.piyush.springboot.reditclone.model.User;
import com.piyush.springboot.reditclone.repository.UserRepository;

import lombok.AllArgsConstructor;
import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	//private final UserRepository userRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUsername(username);
		User user = userOptional.orElseThrow(()-> new UsernameNotFoundException(username+"not found"));
		return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities("USER"));
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(String role){
		return singletonList(new SimpleGrantedAuthority(role));
	}

}
