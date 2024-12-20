package ma.emsi.voiture;

import ma.emsi.voiture.entities.Client;
import ma.emsi.voiture.entities.Voiture;
import ma.emsi.voiture.feign.ClientService;
import ma.emsi.voiture.repositories.VoitureRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class VoitureApplication {

    private final ClientService clientService;

    public VoitureApplication(ClientService clientService) {
        this.clientService = clientService;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoitureApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeData(VoitureRepo voitureRepo) {
        return args -> {
            try {
                // Récupération du client via Feign
                Client client = clientService.clientById(1L);
                if (client != null) {
                    // Création et sauvegarde des voitures
                    voitureRepo.save(new Voiture("123ABC", "Toyota", "Corolla", "2020", client));
                    voitureRepo.save(new Voiture("456DEF", "Ford", "Focus", "2021", client));
                    voitureRepo.save(new Voiture("789GHI", "BMW", "X5", "2022", client));

                    System.out.println("Données initiales chargées avec succès");
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de l'initialisation des données: " + e.getMessage());
            }
        };
    }
}