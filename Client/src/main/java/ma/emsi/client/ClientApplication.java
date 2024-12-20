package ma.emsi.client;

import ma.emsi.client.entities.Client;
import ma.emsi.client.repositories.ClientRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    CommandLineRunner initialiserBaseH2(ClientRepo clientRepo) {
        return args -> {
            clientRepo.save(new Client("Rabab SELIMANI", 23f));
            clientRepo.save(new Client("Amal RAMI", 22f));
            clientRepo.save(new Client("Samir SAFI", 22f));
        };
    }
}
