package com.pfe.gestion.controller;

import com.pfe.gestion.dto.ProjetDTO;
import com.pfe.gestion.entity.Etudiant;
import com.pfe.gestion.entity.Projet;
import com.pfe.gestion.entity.Societe;
import com.pfe.gestion.repository.ProjetRepository;
import com.pfe.gestion.repository.SocieteRepository;
import com.pfe.gestion.service.PfeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/societe")
@CrossOrigin(origins = "*")
public class SocieteController {

    private final ProjetRepository projetRepo;
    private final PfeService pfeService;
    private final SocieteRepository societeRepository;

    public SocieteController(ProjetRepository projetRepo, PfeService pfeService, SocieteRepository societeRepository) {
        this.projetRepo = projetRepo;
        this.pfeService = pfeService;
        this.societeRepository = societeRepository;
    }

    @GetMapping("/all")
    public List<Societe> getAllSocietes() { return societeRepository.findAll(); }

    @GetMapping("/{societeId}/offres")
    public ResponseEntity<List<Projet>> getOffresPubliees(@PathVariable Long societeId) {
        return ResponseEntity.ok(projetRepo.findBySocieteId(societeId));
    }


    @GetMapping("/{societeId}/propositions")
    public ResponseEntity<List<Projet>> getPropositionsAValider(@PathVariable Long societeId) {
        // Hethi l méthode l jdida eli tlawij 3la 'validationSociete = EN_ATTENTE'
        return ResponseEntity.ok(pfeService.getPropositionsPourSociete(societeId));
    }

    @GetMapping("/{societeId}/stagiaires")
    public ResponseEntity<List<Projet>> getStagiaires(@PathVariable Long societeId) {
        return ResponseEntity.ok(pfeService.getStagiairesParSociete(societeId));
    }

    @PostMapping("/{societeId}/publier")
    public ResponseEntity<?> publierOffre(@PathVariable Long societeId, @RequestBody ProjetDTO projetDto) {
        return ResponseEntity.ok(pfeService.publierSujetSociete(societeId, projetDto));
    }

    @PutMapping("/valider/{projetId}")
    public ResponseEntity<?> validerProposition(@PathVariable Long projetId, @RequestParam boolean accepte) {
        return ResponseEntity.ok(pfeService.validerPropositionSociete(projetId, accepte));
    }
}