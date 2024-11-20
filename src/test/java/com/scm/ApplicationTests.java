package com.scm;

import com.scm.Services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;

	@Test
	void sendEmailTest(){
		emailService.sendEmail(
				"mnmuditnagpal@gmail.com",
				"Test Email",
				"this is a test email"
		);
	}

}
