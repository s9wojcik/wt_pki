package pl.assecods.pkitest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import pl.assecods.pkitest.entity.User;
import pl.assecods.pkitest.repository.UserRepository;

@Component
public class AppCommandLineRunner implements CommandLineRunner {
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired UserRepository userRepository;
	
	@Override
	public void run(String... arg0) throws Exception {
		List<User> userList = userRepository.findAll();
		System.out.println("Użytkownicy:");		
		for (User u : userList) {
			System.out.println(u.toString());
		}
	}
}
