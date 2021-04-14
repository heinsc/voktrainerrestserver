package de.heinsc.vokabeltraineronline.postgresrestadapter.vokabeltraineronline.postgresrestadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.heinsc.vokabeltraineronline.postgresrestadapter.vokabeltraineronline.postgresrestadapter.appuser.AppUser;
import de.heinsc.vokabeltraineronline.postgresrestadapter.vokabeltraineronline.postgresrestadapter.appuser.AppUserRepository;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	@Autowired
	AppUserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
    @Override
    public void run(String... args) {

        repository.save(new AppUser("a.b@c.de", "ab".getBytes()));
        repository.save(new AppUser("b.b@c.de", "ac".getBytes()));
        repository.save(new AppUser("c.b@c.de", "ad".getBytes()));
    }

}
