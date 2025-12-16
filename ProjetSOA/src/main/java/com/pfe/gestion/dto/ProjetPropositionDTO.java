package com.pfe.gestion.dto;

public class ProjetPropositionDTO {
    private String titre;
    private String description;
    private Long societeId;
    private Long enseignantId;


    public ProjetPropositionDTO() {
    }


    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public Long getSocieteId() { return societeId; }
    public Long getEnseignantId() { return enseignantId; }


    public void setTitre(String titre) { this.titre = titre; }
    public void setDescription(String description) { this.description = description; }
    public void setSocieteId(Long societeId) { this.societeId = societeId; }
    public void setEnseignantId(Long enseignantId) { this.enseignantId = enseignantId; }
}