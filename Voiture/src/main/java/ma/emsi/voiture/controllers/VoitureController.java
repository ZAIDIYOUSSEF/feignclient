package ma.emsi.voiture.controllers;

import ma.emsi.voiture.entities.Voiture;
import ma.emsi.voiture.services.VoitureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voitures")
public class VoitureController {

    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping
    public List<Voiture> findAll() {
        return voitureService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voiture> findById(@PathVariable Long id) {
        return voitureService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    public List<Voiture> findByClientId(@PathVariable Long clientId) {
        return voitureService.findByClientId(clientId);
    }

    @PostMapping
    public ResponseEntity<Voiture> save(@RequestBody Voiture voiture) {
        return ResponseEntity.ok(voitureService.save(voiture));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        voitureService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voiture> update(@PathVariable Long id, @RequestBody Voiture voiture) {
        if (!id.equals(voiture.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(voitureService.save(voiture));
    }
}