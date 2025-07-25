package com.learning.candidatemanagementsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Candidate Management System",
				description = "APIs to manage candidate operations across different roles (Admin, Recruiter, Candidate)",
				version = "v1",
				contact = @Contact(
						name = "Abhisht Pratap Singh",
						email = "abhisht.pratapsingh@cognizant.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0"
				)
		)
)

public class CandidateManagementSystemApp {

	public static void main(String[] args) {
		SpringApplication.run(CandidateManagementSystemApp.class, args);
	}

}
