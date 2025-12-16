package com.pfe.gestion.controller;

import com.pfe.gestion.entity.Enseignant;
import com.pfe.gestion.entity.Projet;
import com.pfe.gestion.service.PfeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enseignant")
@CrossOrigin(origins = "*")
public class EnseignantController {

    private final PfeService pfeService;

    public EnseignantController(PfeService pfeService) {
        this.pfeService = pfeService;
    }

    // LISTE DES PROFS
    @GetMapping("/all")
    public List<Enseignant> getAllEnseignants() {
        return pfeService.getAllEnseignants();
    }


    //  Tableau de bord (Demandes EN_ATTENTE pour ce prof)
    @GetMapping("/{enseignantId}/demandes")
    public ResponseEntity<List<Projet>> getDemandesEnAttente(@PathVariable Long enseignantId) {

        return ResponseEntity.ok(pfeService.getDemandesPourEnseignant(enseignantId));
    }

    // Mes étudiants validés
    @GetMapping("/{enseignantId}/etudiants")
    public ResponseEntity<List<Projet>> getEtudiantsEncadres(@PathVariable Long enseignantId) {
        return ResponseEntity.ok(pfeService.getEtudiantsEncadres(enseignantId));
    }

    // Decision du prof
    @PutMapping("/decision/{projetId}")
    public ResponseEntity<?> prendreDecision(@PathVariable Long projetId, @RequestParam boolean accepte) {
        try {
            return ResponseEntity.ok(pfeService.decisionEnseignant(projetId, accepte));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}