package com.pfe.gestion.controller;

import com.pfe.gestion.dto.ProjetDTO;
import com.pfe.gestion.entity.Enseignant;
import com.pfe.gestion.entity.Projet;
import com.pfe.gestion.service.PfeService;
import com.pfe.gestion.service.XQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pfe.gestion.entity.Societe;
import java.util.List;

@RestController
@RequestMapping("/api/pfe")
@CrossOrigin(origins = "*")
public class PfeController {

    private final PfeService service;
    private final XQueryService xQueryService;

    // Injection par constructeur (plus propre que @Autowired sur les champs)
    public PfeController(PfeService service, XQueryService xQueryService) {
        this.service = service;
        this.xQueryService = xQueryService;
    }

    // 1. Publier un sujet (Société)
    @PostMapping("/societe/{id}/add")
    public Projet addProjet(@PathVariable Long id, @RequestBody ProjetDTO projetDto) {
        return service.publierSujetSociete(id, projetDto);
    }

    // 2. Liste des sujets disponibles
    @GetMapping("/disponibles")
    public List<Projet> getDisponibles() {
        return service.getSujetsDisponibles();
    }

    // 3. Étudiant choisit un sujet (Scénario 1 : Offre existante)
    @PostMapping("/choisir")
    public Projet choisir(@RequestParam Long projetId, @RequestParam Long etudiantId, @RequestParam Long profId) {
        return service.postulerSurSujet(projetId, etudiantId, profId);
    }

    // 4. Étudiant propose un sujet (Scénario 2 : Idée personnelle)

    @PostMapping("/proposer")
    public Projet proposer(@RequestBody ProjetDTO projetDto,
                           @RequestParam Long etudiantId,
                           @RequestParam Long profId) {

        if(projetDto.getSocieteId() == null) {
            throw new RuntimeException("L'ID de la société est obligatoire.");
        }

        // Appel à la méthode du service (String, String, Long, Long, Long)
        return service.proposerSujet(
                projetDto.getTitre(),
                projetDto.getDescription(),
                etudiantId,
                projetDto.getSocieteId(),
                profId
        );
    }

    // 5. Dashboard Enseignant (Demandes en attente)
    @GetMapping("/enseignant/{id}/dashboard")
    public List<Projet> dashboard(@PathVariable Long id) {
        return service.getDemandesPourEnseignant(id);
    }

    // 6. Validation par l'enseignant
    @PutMapping("/valider/{projetId}")
    public Projet valider(@PathVariable Long projetId, @RequestParam boolean accepte) {
        return service.decisionEnseignant(projetId, accepte);
    }

    // 7. Test XQuery
    @GetMapping("/xquery")
    public List<String> testXQuery(@RequestParam String societe) {
        return xQueryService.filtrerParSocieteViaXQuery(societe);
    }

    // 8. Liste des enseignants
    @GetMapping("/enseignants")
    public List<Enseignant> getEnseignants() {
        return service.getAllEnseignants();
    }

    // 9. Propositions reçues par une société
    @GetMapping("/societe/{id}/propositions")
    public List<Projet> getPropositions(@PathVariable Long id) {
        return service.getPropositionsPourSociete(id);
    }

    // 10. Liste de tous les projets affectés (Validés)
    @GetMapping("/projets/affectes")
    public ResponseEntity<List<Projet>> getTousLesProjetsAffectes() {
        List<Projet> projets = service.getTousLesProjetsAffectes();
        return ResponseEntity.ok(projets);
    }


    // 11. Liste de toutes les sociétés (Pour la liste déroulante)
    @GetMapping("/societes")
    public List<Societe> getSocietes() {
        return service.getAllSocietes();
    }


    // 12. Vérifier l'état du projet d'un étudiant
    @GetMapping("/etudiant/{id}/projet")
    public ResponseEntity<?> getProjetEtudiant(@PathVariable Long id) {
        // On cherche le projet de l'étudiant
        Projet projet = service.getProjetParEtudiant(id);

        if (projet == null) {
            // Si aucun projet, on renvoie un code 204 (No Content) ou null
            return ResponseEntity.noContent().build();
        }
        // Sinon on renvoie le projet
        return ResponseEntity.ok(projet);
    }
    // DANS PfeController.java

    // 13. Endpoint pour l'onglet "Mes Stagiaires"
    @GetMapping("/societe/{id}/stagiaires")
    public List<Projet> getStagiairesSociete(@PathVariable Long id) {
        return service.getStagiairesParSociete(id);
    }

    // 14. Endpoint pour le bouton "Accepter / Refuser" de la société
    @PutMapping("/societe/valider/{projetId}")
    public Projet validerSociete(@PathVariable Long projetId, @RequestParam boolean accepte) {
        return service.validerPropositionSociete(projetId, accepte);
    }

    // Dans PfeController.java

    @PutMapping("/etudiant/corriger/{projetId}")
    public Projet corrigerProjet(
            @PathVariable Long projetId,
            @RequestParam(required = false) Long societeId,
            @RequestParam(required = false) Long enseignantId) {

        return service.corrigerProjet(projetId, societeId, enseignantId);
    }
}