package com.project;

import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.model.Role;
import com.project.model.User;
import com.project.service.UserService;

@SpringBootApplication
public class ShoeProjectDemoApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ShoeProjectDemoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		userService.delete("tuan");
		
		User admin = new User();
	    admin.setUsername("tuan");
	    admin.setPassword("tuan");
	    admin.setEmail("tuan@email.com");
	    admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

	    userService.signup(admin);
	}

	@Bean
	public WebMvcConfigurer CORSConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
		};
	}

}
