package com.pfe.gestion.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private EtatProjet etat = EtatProjet.DISPONIBLE; // Valeur par défaut

    // Pour les projets apportés par l'étudiant (externe)
    private String nomSocieteExterne;



    // Plusieurs projets peuvent appartenir à une même société
    @ManyToOne
    @JoinColumn(name = "societe_id")
    // Ignore la liste des projets dans l'objet Societe pour éviter la boucle infinie
    @JsonIgnoreProperties("projets")
    private Societe societe;

    // Un projet a au maximum UN étudiant (et un étudiant a UN projet)
    // unique = true empêche d'assigner le même étudiant à deux projets différents
    @OneToOne
    @JoinColumn(name = "etudiant_id", unique = true)
    // Ignore les détails complexes de l'étudiant si nécessaire pour alléger la réponse
    @JsonIgnoreProperties({"motDePasse", "projets"})
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    @JsonIgnoreProperties("projets")
    private Enseignant enseignant;
// ... autres champs existants ...


    // Valeurs possibles : "EN_ATTENTE", "VALIDE", "REFUSE"
    private String validationSociete = "EN_ATTENTE";
    private String validationEnseignant = "EN_ATTENTE";

    // Getters et Setters
    public String getValidationSociete() { return validationSociete; }
    public void setValidationSociete(String validationSociete) { this.validationSociete = validationSociete; }

    public String getValidationEnseignant() { return validationEnseignant; }
    public void setValidationEnseignant(String validationEnseignant) { this.validationEnseignant = validationEnseignant; }
    // --- CONSTRUCTEURS ---

    public Projet() {
        // Constructeur vide requis par JPA
    }

    public Projet(String titre, String description, Societe societe) {
        this.titre = titre;
        this.description = description;
        this.societe = societe;
        this.etat = EtatProjet.DISPONIBLE;
    }

    // --- GETTERS ET SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public EtatProjet getEtat() { return etat; }
    public void setEtat(EtatProjet etat) { this.etat = etat; }

    public String getNomSocieteExterne() { return nomSocieteExterne; }
    public void setNomSocieteExterne(String nomSocieteExterne) { this.nomSocieteExterne = nomSocieteExterne; }

    public Societe getSociete() { return societe; }
    public void setSociete(Societe societe) { this.societe = societe; }

    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }

    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }
}