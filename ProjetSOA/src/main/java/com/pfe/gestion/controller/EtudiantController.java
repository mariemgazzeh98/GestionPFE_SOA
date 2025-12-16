package com.pfe.gestion.controller;

import com.pfe.gestion.dto.ProjetPropositionDTO; // Import DTO si besoin
import com.pfe.gestion.entity.Enseignant;
import com.pfe.gestion.entity.Projet;
import com.pfe.gestion.service.PfeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiant")
@CrossOrigin(origins = "*")
public class EtudiantController {

    private final PfeService pfeService;

    public EtudiantController(PfeService pfeService) {
        this.pfeService = pfeService;
    }

    // 1. Récupérer le projet
    @GetMapping("/{etudiantId}/projet")
    public ResponseEntity<Projet> getMonProjet(@PathVariable Long etudiantId) {
        Projet projet = pfeService.getProjetParEtudiant(etudiantId);
        if (projet == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(projet);
    }

    // 2. Liste des enseignants
    @GetMapping("/enseignants")
    public ResponseEntity<List<Enseignant>> getTousLesEnseignants() {
        return ResponseEntity.ok(pfeService.getAllEnseignants());
    }

    // 3. Sujets disponibles
    @GetMapping("/sujets-disponibles")
    public ResponseEntity<List<Projet>> getSujetsDisponibles() {
        return ResponseEntity.ok(pfeService.getSujetsDisponibles());
    }

    // 4. Postuler (Option A)
    @PostMapping("/{etudiantId}/postuler")
    public ResponseEntity<?> postuler(
            @PathVariable Long etudiantId,
            @RequestParam Long projetId,
            @RequestParam Long enseignantId) {
        try {
            Projet p = pfeService.postulerSurSujet(projetId, etudiantId, enseignantId);
            return ResponseEntity.ok(p);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  Proposer (Option B)

    @PostMapping("/{etudiantId}/proposer")
    public ResponseEntity<?> proposerSujet(
            @PathVariable Long etudiantId,
            @RequestBody ProjetPropositionDTO dto, // Utilise le DTO {titre, description, societeId}
            @RequestParam Long profId) { // Le profId est passé en param url dans ton JS
        try {
            Projet p = pfeService.proposerSujet(
                    dto.getTitre(),
                    dto.getDescription(),
                    etudiantId,
                    dto.getSocieteId(),
                    profId
            );
            return ResponseEntity.ok(p);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Cette méthode permet de corriger la société OU l'enseignant en cas de refus
    @PutMapping("/corriger/{projetId}")
    public ResponseEntity<?> corrigerProjet(
            @PathVariable Long projetId,
            @RequestParam(required = false) Long societeId,
            @RequestParam(required = false) Long enseignantId) {
        try {
            // Appel de la nouvelle méthode du Service
            Projet p = pfeService.corrigerProjet(projetId, societeId, enseignantId);
            return ResponseEntity.ok(p);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}