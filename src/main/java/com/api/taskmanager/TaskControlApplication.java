package com.api.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TaskControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskControlApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "Tomcat started on port(s): 8080";
	}
}
