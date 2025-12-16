package com.pfe.gestion.repository;

import com.pfe.gestion.entity.EtatProjet;
import com.pfe.gestion.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

    // 1. Pour l'etudiant (Verifier s'il a un projet)
    List<Projet> findByEtudiantId(Long etudiantId);
    boolean existsByEtudiantId(Long etudiantId);

    // 2. Pour Option A (Sujets Disponibles)
    List<Projet> findByEtat(EtatProjet etat);

    // 3. Pour Societe (Offres publiées par elle)
    List<Projet> findBySocieteId(Long societeId);

    List<Projet> findByEnseignantIdAndValidationEnseignant(Long enseignantId, String validationEnseignant);

    List<Projet> findBySocieteIdAndValidationSociete(Long societeId, String validationSociete);

    // 4. Pour les listes finales (Stagiaires / Validés)
    List<Projet> findByEnseignantIdAndEtat(Long enseignantId, EtatProjet etat);
    List<Projet> findBySocieteIdAndEtat(Long societeId, EtatProjet etat);
}