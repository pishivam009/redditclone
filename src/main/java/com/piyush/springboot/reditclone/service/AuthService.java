package com.piyush.springboot.reditclone.service;

//import java.time.Instant;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.piyush.springboot.reditclone.dto.AuthenticationResponse;
//import com.piyush.springboot.reditclone.dto.LoginRequest;
//import com.piyush.springboot.reditclone.dto.RegisterRequest;
//import com.piyush.springboot.reditclone.exceptions.SpringRedditException;
//import com.piyush.springboot.reditclone.model.NotificationEmail;
//import com.piyush.springboot.reditclone.model.User;
//import com.piyush.springboot.reditclone.model.VerificationToken;
//import com.piyush.springboot.reditclone.repository.UserRepository;
//import com.piyush.springboot.reditclone.repository.VerificationTokenRepository;
//import com.piyush.springboot.reditclone.security.JwtProvider;
//
//import lombok.AllArgsConstructor;
//
//@Service
//@AllArgsConstructor
//@Transactional
//public class AuthService {
//
//	private final PasswordEncoder passwordEncoder;
//	private final UserRepository userRepository;
//	private final VerificationTokenRepository verificationTokenRepository;
//	private final MailService mailService;
//	private final AuthenticationManager authenticationManager;
//	private final JwtProvider jwtProvider;
//
////	@Autowired
////	PasswordEncoder passwordEncoder;
////	@Autowired
////	UserRepository userRepository;
////	@Autowired
////	VerificationTokenRepository verificationTokenRepository;
////	@Autowired
////	MailService mailService;
////	@Autowired
////	AuthenticationManager authenticationManager;
////	@Autowired
////	JwtProvider jwtProvider;
//
//	public void signup(RegisterRequest registerRequest) {
//		User user = new User();
//		user.setUsername(registerRequest.getUsername());
//		user.setEmail(registerRequest.getEmail());
//		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//		user.setCreated(Instant.now());
//		user.setEnabled(false);
//
//		userRepository.save(user);
//		String token = generateVerificationToken(user);
//		mailService.sendMail(new NotificationEmail("Please Activate Your Account", user.getEmail(),
//				"Thank you so much to signing up for Spring Reddit,"
//						+ " please click on the link below to activate your account: "
//						+ "http://localhost:8080/api/auth/accountVerification/" + token));
//
//	}
//
//	private String generateVerificationToken(User user) {
//		String token = UUID.randomUUID().toString();
//		VerificationToken verificationToken = new VerificationToken();
//		verificationToken.setToken(token);
//		verificationToken.setUser(user);
//		verificationTokenRepository.save(verificationToken);
//		return token;
//	}
//
//	public void verifyAccount(String token) {
//		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
//		fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
//	}
//
//	private void fetchUserAndEnable(VerificationToken verificationToken) {
//		String username = verificationToken.getUser().getUsername();
//		User user = userRepository.findByUsername(username)
//				.orElseThrow(() -> new SpringRedditException("User not found with name " + username));
//		user.setEnabled(true);
//		userRepository.save(user);
//
//	}
//
////	public AuthenticationResponse login(LoginRequest loginRequest) {
////		Authentication authenticate = authenticationManager.authenticate(
////				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
////		SecurityContextHolder.getContext().setAuthentication(authenticate);
////		String token = jwtProvider.generateToken(authenticate);
////		
////		//return AuthenticationResponse.builder().authenticationToken(token).username(loginRequest.getUsername()).build();
////		return new AuthenticationResponse(token, loginRequest.getUsername());
////	}
//	public AuthenticationResponse login(LoginRequest loginRequest) {
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
//                loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        String token = jwtProvider.generateToken(authenticate);
//        return AuthenticationResponse.builder()
//                .authenticationToken(token)
//                .username(loginRequest.getUsername())
//                .build();
//    }
//
//}

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.piyush.springboot.reditclone.dto.AuthenticationResponse;
import com.piyush.springboot.reditclone.dto.LoginRequest;
import com.piyush.springboot.reditclone.dto.RegisterRequest;
import com.piyush.springboot.reditclone.exceptions.SpringRedditException;
import com.piyush.springboot.reditclone.model.NotificationEmail;
import com.piyush.springboot.reditclone.model.User;
import com.piyush.springboot.reditclone.model.VerificationToken;
import com.piyush.springboot.reditclone.repository.UserRepository;
import com.piyush.springboot.reditclone.repository.VerificationTokenRepository;
import com.piyush.springboot.reditclone.security.JwtProvider;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .username(loginRequest.getUsername())
                .build();
    }

}
