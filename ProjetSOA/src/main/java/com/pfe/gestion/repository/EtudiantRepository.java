package com.pfe.gestion.repository;
import com.pfe.gestion.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Etudiant findByNomAndMotDePasse(String nom, String motDePasse);
}