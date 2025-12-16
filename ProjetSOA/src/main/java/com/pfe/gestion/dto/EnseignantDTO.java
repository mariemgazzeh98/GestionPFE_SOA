package com.pfe.gestion.dto;

public class EnseignantDTO {
    private Long id;
    private String nom;

    public EnseignantDTO(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getters
    public Long getId() { return id; }
    public String getNom() { return nom; }

    public class ProjetPropositionDTO {
        private String titre;
        private String description;
        private Long societeId;
        private Long enseignantId;


    }

}