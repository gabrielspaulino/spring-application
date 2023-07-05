package com.myprojects.course.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myprojects.course.entities.User;
import com.myprojects.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
//ComandLineRunner to execute when the app starts
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		User u1 = new User(null, "Gabriel", "gabriel@gmail.com", "999999999", "12345");
		User u2 = new User(null, "Maria", "maria@gmail.com", "988888888", "12345");
		
		userRepository.saveAll(Arrays.asList(u1, u2));
	}
}
