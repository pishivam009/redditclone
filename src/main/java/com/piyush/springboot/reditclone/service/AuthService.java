package com.piyush.springboot.reditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.piyush.springboot.reditclone.dto.RegisterRequest;
import com.piyush.springboot.reditclone.exceptions.SpringRedditException;
import com.piyush.springboot.reditclone.model.NotificationEmail;
import com.piyush.springboot.reditclone.model.User;
import com.piyush.springboot.reditclone.model.VerificationToken;
import com.piyush.springboot.reditclone.repository.UserRepository;
import com.piyush.springboot.reditclone.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;

	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);

		userRepository.save(user);
		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate Your Account", user.getEmail(),
				"Thank you so much to signing up for Spring Reddit,"
						+ " please click on the link below to activate your account: "
						+ "http://localhost:8080/api/auth/accountVerification/" + token));

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

	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new SpringRedditException("User not found with name " + username));
		user.setEnabled(true);
		userRepository.save(user);

	}

}
