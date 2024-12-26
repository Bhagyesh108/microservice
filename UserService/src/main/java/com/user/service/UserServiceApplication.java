package com.user.service;

import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication extends MySQL8Dialect {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
