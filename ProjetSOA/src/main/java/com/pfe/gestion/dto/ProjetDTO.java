package com.pfe.gestion.dto;

public class ProjetDTO {

    private Long id;
    private String titre;
    private String description;
    private String etat;

    private String nomEtudiant;
    private Long etudiantId;

    private String nomEnseignant;
    private Long enseignantId;

    private String nomSociete;
    private Long societeId;

    public ProjetDTO() {
    }

    public ProjetDTO(Long id, String titre, String description, String etat, String nomSociete) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.nomSociete = nomSociete;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public String getNomEtudiant() { return nomEtudiant; }
    public void setNomEtudiant(String nomEtudiant) { this.nomEtudiant = nomEtudiant; }

    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }

    public String getNomEnseignant() { return nomEnseignant; }
    public void setNomEnseignant(String nomEnseignant) { this.nomEnseignant = nomEnseignant; }

    public Long getEnseignantId() { return enseignantId; }
    public void setEnseignantId(Long enseignantId) { this.enseignantId = enseignantId; }

    public String getNomSociete() { return nomSociete; }
    public void setNomSociete(String nomSociete) { this.nomSociete = nomSociete; }

    public Long getSocieteId() { return societeId; }
    public void setSocieteId(Long societeId) { this.societeId = societeId; }
}