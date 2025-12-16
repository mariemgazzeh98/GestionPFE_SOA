package com.pfe.gestion.mapper;

import com.pfe.gestion.dto.ProjetDTO;
import com.pfe.gestion.entity.EtatProjet;
import com.pfe.gestion.entity.Projet;
import org.springframework.stereotype.Component;

@Component
public class ProjetMapper {

    // Transforme l'Entité (BDD) vers le DTO (JSON)
    public ProjetDTO toDTO(Projet projet) {
        if (projet == null) {
            return null;
        }

        ProjetDTO dto = new ProjetDTO();

        // 1. Champs simples
        dto.setId(projet.getId());
        dto.setTitre(projet.getTitre());
        dto.setDescription(projet.getDescription());

        // Conversion de l'Enum en String
        if (projet.getEtat() != null) {
            dto.setEtat(projet.getEtat().toString());
        }

        // 2. Gestion de la Société (Hybride : Soit interne, soit externe)
        if (projet.getSociete() != null) {
            // C'est une société inscrite dans la base
            dto.setNomSociete(projet.getSociete().getNom());
            dto.setSocieteId(projet.getSociete().getId());
        } else {
            // C'est une société externe (saisie libre ou proposition étudiant)
            dto.setNomSociete(projet.getNomSocieteExterne());
        }

        // 3. Gestion de l'Étudiant (si assigné)
        if (projet.getEtudiant() != null) {
            dto.setNomEtudiant(projet.getEtudiant().getNom());
            dto.setEtudiantId(projet.getEtudiant().getId());
        }

        // 4. Gestion de l'Enseignant (si assigné)
        if (projet.getEnseignant() != null) {
            dto.setNomEnseignant(projet.getEnseignant().getNom());
            dto.setEnseignantId(projet.getEnseignant().getId());
        }

        return dto;
    }

    // Transforme le DTO (JSON) vers l'Entité (BDD)
    // Note : On ne mappe que les champs simples ici. Les relations (IDs)
    // sont généralement gérées dans le Service (PfeService) via les Repositories.
    public Projet toEntity(ProjetDTO dto) {
        if (dto == null) {
            return null;
        }

        Projet projet = new Projet();
        projet.setId(dto.getId());
        projet.setTitre(dto.getTitre());
        projet.setDescription(dto.getDescription());
        projet.setNomSocieteExterne(dto.getNomSociete());

        if (dto.getEtat() != null) {
            try {
                projet.setEtat(EtatProjet.valueOf(dto.getEtat()));
            } catch (IllegalArgumentException e) {
                // Par défaut si l'état n'est pas reconnu
                projet.setEtat(EtatProjet.DISPONIBLE);
            }
        } else {
            projet.setEtat(EtatProjet.DISPONIBLE);
        }

        return projet;
    }
}