package jwt.spring.usersecurity;

import jwt.spring.usersecurity.domain.Role;
import jwt.spring.usersecurity.domain.User;
import jwt.spring.usersecurity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class UsersecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersecurityApplication.class, args);
	}

	// this will run after the application runs
	@Bean
	CommandLineRunner runner (UserService userService){
		return args -> {
			// created some roles
			userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_MANAGER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));
			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

			// created some users
			userService.saveUser(new User(null,"Mahdi bouaziz","mahdi","mahdi123",new ArrayList<>()));
			userService.saveUser(new User(null,"Ramez ben aribiya","ramez","mahdi123",new ArrayList<>()));
			userService.saveUser(new User(null,"sarra ben youssef","sarra","mahdi123",new ArrayList<>()));
			userService.saveUser(new User(null,"rima edhib","rima","mahdi123",new ArrayList<>()));

			// add roles to users
			userService.addRoleToUser("mahdi","ROLE_SUPER_ADMIN");
			userService.addRoleToUser("mahdi","ROLE_MANAGER");
			userService.addRoleToUser("mahdi","ROLE_ADMIN");
			userService.addRoleToUser("ramez","ROLE_ADMIN");
			userService.addRoleToUser("sarra","ROLE_USER");
			userService.addRoleToUser("rima","ROLE_USER");

		};
	}

}
