package noccures.clipperms;

import noccures.clipperms.model.AppRole;
import noccures.clipperms.model.AppUser;
import noccures.clipperms.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.UUID;


@SpringBootApplication
public class ClipperMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClipperMsApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(AppUserService userService){
		return args -> {
			userService.saveRole(new AppRole( UUID.randomUUID(), "ROLE_USER"));
			userService.saveRole(new AppRole(UUID.randomUUID(), "ROLE_ADMIN"));
			userService.saveRole(new AppRole(UUID.randomUUID(), "ROLE_SUPER_ADMIN"));

			userService.saveUser(new AppUser(UUID.randomUUID(), "john doe", "john", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
			userService.saveUser(new AppUser(UUID.randomUUID(), "maarten", "maarten", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
			userService.saveUser(new AppUser(UUID.randomUUID(), "snoop dog", "do2g", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

			userService.addRoleToUser("maarten", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("maarten", "ROLE_ADMIN");
			userService.addRoleToUser("john", "ROLE_USER");
			userService.addRoleToUser("do2g", "ROLE_ADMIN");
		};
	}
}
