package interview.jobsource;

import interview.jobsource.models.Client;
import interview.jobsource.models.Position;
import interview.jobsource.models.Role;
import interview.jobsource.services.ClientService;
import interview.jobsource.services.PositionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class JobSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSourceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ClientService clientService, PositionService positionService){
		return args -> {
			clientService.saveRole(new Role(null, "ROLE_USER"));
			clientService.saveRole(new Role(null, "ROLE_ADMIN"));

			clientService.saveClient(new Client(null,"Nagy Ferenc","nagyferenc@gmail.com","password", new HashSet<Role>()));
			clientService.saveClient(new Client(null,"Kiss János","kissjanos@gmail.com","password", new HashSet<Role>()));
			clientService.saveClient(new Client(null,"Varga Béla","vargabela@gmail.com","password", new HashSet<Role>()));

			positionService.savePosition(new Position(null, "Greatest Job", "Budapest"));
			positionService.savePosition(new Position(null, "Lonely Job", "Budapest"));
			positionService.savePosition(new Position(null, "We have no clue", "Győr"));
		};
	}
}
