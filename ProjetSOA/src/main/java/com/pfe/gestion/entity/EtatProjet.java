package com.pfe.gestion.entity;

public enum EtatProjet {
    DISPONIBLE,          // Offre publiée, personne dessus
    ATTENTE_SOCIETE,     // (Option B) Attente validation société
    ATTENTE_PROF,        // (Option A) Attente validation prof
    EN_COURS_VALIDATION, // (Nouveau) Pour la validation parallèle Option B
    AFFECTE,             // Validé final
    REFUSE_PROF,         // Refusé par le prof
    REFUSE_SOC           // Refusé par la société
}