package com.pfe.gestion.repository;
import com.pfe.gestion.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    Enseignant findByNomAndMotDePasse(String nom, String motDePasse);
}