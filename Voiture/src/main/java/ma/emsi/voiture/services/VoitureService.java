package ma.emsi.voiture.services;

import ma.emsi.voiture.entities.Voiture;
import ma.emsi.voiture.feign.ClientService;
import ma.emsi.voiture.repositories.VoitureRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoitureService {
    private final VoitureRepo voitureRepo;
    private final ClientService clientService;

    public VoitureService(VoitureRepo voitureRepo, ClientService clientService) {
        this.voitureRepo = voitureRepo;
        this.clientService = clientService;
    }

    public List<Voiture> findAll() {
        List<Voiture> voitures = voitureRepo.findAll();
        voitures.forEach(voiture -> {
            try {
                voiture.setClient(clientService.clientById(voiture.getClientId()));
            } catch (Exception e) {
                // Log l'erreur mais continue le traitement
                System.err.println("Erreur lors de la récupération du client " + voiture.getClientId() + ": " + e.getMessage());
            }
        });
        return voitures;
    }

    public Optional<Voiture> findById(Long id) {
        Optional<Voiture> voiture = voitureRepo.findById(id);
        voiture.ifPresent(v -> {
            try {
                v.setClient(clientService.clientById(v.getClientId()));
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération du client pour la voiture " + id + ": " + e.getMessage());
            }
        });
        return voiture;
    }

    public List<Voiture> findByClientId(Long clientId) {
        return voitureRepo.findAll().stream()
                .filter(voiture -> clientId.equals(voiture.getClientId()))
                .map(voiture -> {
                    try {
                        voiture.setClient(clientService.clientById(clientId));
                    } catch (Exception e) {
                        System.err.println("Erreur lors de la récupération du client " + clientId + ": " + e.getMessage());
                    }
                    return voiture;
                })
                .collect(Collectors.toList());
    }

    public Voiture save(Voiture voiture) {
        // Si la voiture a un client, mettre à jour le clientId
        if (voiture.getClient() != null) {
            voiture.setClientId(voiture.getClient().getId());
        }

        // Sauvegarder la voiture
        Voiture savedVoiture = voitureRepo.save(voiture);

        // Récupérer les informations complètes du client
        try {
            if (savedVoiture.getClientId() != null) {
                savedVoiture.setClient(clientService.clientById(savedVoiture.getClientId()));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du client après sauvegarde: " + e.getMessage());
        }

        return savedVoiture;
    }

    public void deleteById(Long id) {
        voitureRepo.deleteById(id);
    }

    public Voiture update(Long id, Voiture voiture) {
        if (voitureRepo.existsById(id)) {
            voiture.setId(id);
            return save(voiture);
        }
        return null;
    }
}