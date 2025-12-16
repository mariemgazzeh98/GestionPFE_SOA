package com.pfe.gestion.repository;
import com.pfe.gestion.entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SocieteRepository extends JpaRepository<Societe, Long> {
    Societe findFirstByNom(String nom);
}