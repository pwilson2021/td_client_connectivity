package turntabl.io.client_connectivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClientConnectivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientConnectivityApplication.class, args);
	}

}
