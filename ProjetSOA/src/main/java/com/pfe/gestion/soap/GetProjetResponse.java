package com.pfe.gestion.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://pfe.com/soap", name = "getProjetResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetProjetResponse {
    private String titre;
    private String etat;
    private String etudiant;

    // Getters et Setters obligatoires
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public String getEtudiant() { return etudiant; }
    public void setEtudiant(String etudiant) { this.etudiant = etudiant; }
}