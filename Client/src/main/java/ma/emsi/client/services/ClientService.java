package ma.emsi.client.services;

import ma.emsi.client.entities.Client;
import ma.emsi.client.repositories.ClientRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepo clientRepo;

    // Constructeur
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    // Fetch all clients
    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    // Save or update a client
    public Client save(Client client) {
        // Validate age is positive
        if (client.getAge() <= 0) {
            throw new IllegalArgumentException("L'âge doit être positif.");
        }

        // Validate name is not null or empty
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }

        // Check if a client with the same name already exists
        Optional<Client> existingClient = clientRepo.findAll()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(client.getName()))
                .findFirst();

        if (existingClient.isPresent()) {
            // Update the existing client if it exists
            Client updatedClient = existingClient.get();
            updatedClient.setAge(client.getAge());
            return clientRepo.save(updatedClient);
        } else {
            // Save the new client
            return clientRepo.save(client);
        }
    }

    // Find client by ID
    public Optional<Client> findById(Long id) {
        return clientRepo.findById(id);
    }

    // Delete client by ID
    public void deleteById(Long id) {
        if (!clientRepo.existsById(id)) {
            throw new IllegalArgumentException("Client avec ID " + id + " n'existe pas.");
        }
        clientRepo.deleteById(id);
    }
}
