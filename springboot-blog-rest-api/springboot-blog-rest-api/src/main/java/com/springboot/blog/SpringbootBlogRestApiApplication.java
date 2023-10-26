package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title="Spring boot blog applicatoin Rest Apis",
		description="Spring Boot Blog App REst APIs Description",
		version="v1.0",
		contact = @Contact(
				name="Gajanan",
				email="gajanan@gmail.com",
				url= "www.help.com"
				),
		license= @License(
				name="Aapache 2.0",
				url="https://www.help.com/license"
				
				) 
		),
		externalDocs = @ExternalDocumentation(
				description="Spring boot blog REst APIs Description",
				url="github link"
				)
  )
public class SpringbootBlogRestApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(String... args) throws Exception {
		 
		Role role=new Role();
		role.setName("ROLE_USER");
		roleRepository.save(role);
		
		Role role1=new Role();
		role1.setName("ROLE_ADMIN");
		roleRepository.save(role1);
	}

}
