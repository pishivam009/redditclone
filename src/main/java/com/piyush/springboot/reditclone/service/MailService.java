package com.piyush.springboot.reditclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.piyush.springboot.reditclone.exceptions.SpringRedditException;
import com.piyush.springboot.reditclone.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

//	private final JavaMailSender mailSender;
//	private final MailContentBuilder mailContentBuilder;
	@Autowired
	MailContentBuilder mailContentBuilder;
	@Autowired
	JavaMailSender mailSender;

	@Async
	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
		};

		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent!!");
		} catch (MailException e) {
			throw new SpringRedditException(
					"Error occurred while sending the mail to" + notificationEmail.getRecipient());
		}
	}
}
