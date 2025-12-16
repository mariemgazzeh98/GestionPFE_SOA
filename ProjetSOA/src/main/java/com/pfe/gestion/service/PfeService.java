package com.pfe.gestion.service;

import com.pfe.gestion.dto.ProjetDTO;
import com.pfe.gestion.dto.ProjetPropositionDTO;
import com.pfe.gestion.entity.*;
import com.pfe.gestion.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PfeService {

    private final ProjetRepository projetRepo;
    private final EtudiantRepository etudiantRepo;
    private final EnseignantRepository enseignantRepo;
    private final SocieteRepository societeRepo;

    public PfeService(ProjetRepository projetRepo, EtudiantRepository etudiantRepo, EnseignantRepository enseignantRepo, SocieteRepository societeRepo) {
        this.projetRepo = projetRepo;
        this.etudiantRepo = etudiantRepo;
        this.enseignantRepo = enseignantRepo;
        this.societeRepo = societeRepo;
    }

    public List<Projet> getDemandesPourEnseignant(Long enseignantId) {
        // On cherche ce qui est EN_ATTENTE spécialement pour ce prof
        return projetRepo.findByEnseignantIdAndValidationEnseignant(enseignantId, "EN_ATTENTE");
    }

    public List<Projet> getPropositionsPourSociete(Long societeId) {
        // On cherche ce qui est EN_ATTENTE spécialement pour cette société
        return projetRepo.findBySocieteIdAndValidationSociete(societeId, "EN_ATTENTE");
    }
    // -------------------------------------------------------------

    // Option A : Postuler
    public Projet postulerSurSujet(Long projetId, Long etudiantId, Long enseignantId) {
        if (projetRepo.existsByEtudiantId(etudiantId)) throw new RuntimeException("Déjà un projet !");
        Projet p = projetRepo.findById(projetId).orElseThrow();
        p.setEtudiant(etudiantRepo.findById(etudiantId).orElseThrow());
        p.setEnseignant(enseignantRepo.findById(enseignantId).orElseThrow());

        p.setEtat(EtatProjet.EN_COURS_VALIDATION);
        p.setValidationSociete("VALIDE"); // Car Option A (Offre Societe)
        p.setValidationEnseignant("EN_ATTENTE");
        return projetRepo.save(p);
    }

    // Option B : Proposer
    public Projet proposerSujet(String titre, String description, Long etudiantId, Long societeId, Long enseignantId) {
        if (projetRepo.existsByEtudiantId(etudiantId)) throw new RuntimeException("Déjà un projet !");
        Projet p = new Projet();
        p.setTitre(titre); p.setDescription(description);
        p.setEtudiant(etudiantRepo.findById(etudiantId).orElseThrow());
        p.setSociete(societeRepo.findById(societeId).orElseThrow());
        p.setEnseignant(enseignantRepo.findById(enseignantId).orElseThrow());


        p.setEtat(EtatProjet.EN_COURS_VALIDATION);
        p.setValidationSociete("EN_ATTENTE");
        p.setValidationEnseignant("EN_ATTENTE");

        return projetRepo.save(p);
    }

    public Projet proposerProjet(Long eid, ProjetPropositionDTO dto) {
        return proposerSujet(dto.getTitre(), dto.getDescription(), eid, dto.getSocieteId(), dto.getEnseignantId());
    }

    // Validation Societe
    public Projet validerPropositionSociete(Long pid, boolean accepte) {
        Projet p = projetRepo.findById(pid).orElseThrow();
        p.setValidationSociete(accepte ? "VALIDE" : "REFUSE");
        checkGlobal(p);
        return projetRepo.save(p);
    }

    // Validation Prof
    public Projet decisionEnseignant(Long pid, boolean accepte) {
        Projet p = projetRepo.findById(pid).orElseThrow();
        p.setValidationEnseignant(accepte ? "VALIDE" : "REFUSE");
        // Cas spécial Option A (si prof refuse, on libère l'étudiant)
        if (!accepte && "VALIDE".equals(p.getValidationSociete()) && p.getDescription().contains("(Option A)")) {
            p.setEtat(EtatProjet.REFUSE_PROF);
            p.setEnseignant(null); // Libérer le prof
            return projetRepo.save(p);
        }
        checkGlobal(p);
        return projetRepo.save(p);
    }

    private void checkGlobal(Projet p) {
        boolean sOk = "VALIDE".equals(p.getValidationSociete());
        boolean pOk = "VALIDE".equals(p.getValidationEnseignant());
        if (sOk && pOk) p.setEtat(EtatProjet.AFFECTE);
        else if ("REFUSE".equals(p.getValidationSociete())) p.setEtat(EtatProjet.REFUSE_SOC);
        else if ("REFUSE".equals(p.getValidationEnseignant())) p.setEtat(EtatProjet.REFUSE_PROF);
        else p.setEtat(EtatProjet.EN_COURS_VALIDATION);
    }

    // Etudiant
    public Projet corrigerProjet(Long pid, Long sid, Long eid) {
        Projet p = projetRepo.findById(pid).orElseThrow();
        if(sid != null) { p.setSociete(societeRepo.findById(sid).orElseThrow()); p.setValidationSociete("EN_ATTENTE"); }
        if(eid != null) { p.setEnseignant(enseignantRepo.findById(eid).orElseThrow()); p.setValidationEnseignant("EN_ATTENTE"); }
        p.setEtat(EtatProjet.EN_COURS_VALIDATION);
        return projetRepo.save(p);
    }

    // Autres méthodes
    public Projet getProjetParEtudiant(Long id) { return projetRepo.findByEtudiantId(id).stream().findFirst().orElse(null); }
    public List<Enseignant> getAllEnseignants() { return enseignantRepo.findAll(); }
    public List<Societe> getAllSocietes() { return societeRepo.findAll(); }
    public List<Projet> getSujetsDisponibles() { return projetRepo.findByEtat(EtatProjet.DISPONIBLE); }
    public List<Projet> getStagiairesParSociete(Long id) { return projetRepo.findBySocieteIdAndEtat(id, EtatProjet.AFFECTE); }
    public List<Projet> getTousLesProjetsAffectes() { return projetRepo.findByEtat(EtatProjet.AFFECTE); }
    public List<Projet> getEtudiantsEncadres(Long id) { return projetRepo.findByEnseignantIdAndEtat(id, EtatProjet.AFFECTE); }

    public Projet publierSujetSociete(Long id, ProjetDTO dto) {
        Societe s = societeRepo.findById(id).orElseThrow();
        Projet p = new Projet();
        p.setTitre(dto.getTitre()); p.setDescription(dto.getDescription() + " (Option A)");
        p.setSociete(s);
        p.setEtat(EtatProjet.DISPONIBLE);
        p.setValidationSociete("VALIDE");
        p.setValidationEnseignant("EN_ATTENTE");
        return projetRepo.save(p);
    }
}