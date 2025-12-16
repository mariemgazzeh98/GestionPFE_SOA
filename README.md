🎓 Gestion PFE - Plateforme de Gestion des Projets de Fin d'Études
📝 Description

Gestion PFE est une application web conçue pour centraliser, simplifier et automatiser le processus de gestion des Projets de Fin d'Études.

Basée sur une Architecture Orientée Services (SOA), elle connecte les trois acteurs principaux du processus académique :

    L'Étudiant
    L'Enseignant
    La Société (Partenaire)

L'objectif est d'éliminer les lourdeurs administratives, d'assurer un suivi en temps réel des demandes et de garantir une coordination fluide grâce à un système de validation interactif.
🚀 Architecture & Technologies

Ce projet adopte une approche moderne avec un Couplage Faible et respecte les standards Jakarta EE.

    Langage : Java 18
    Framework Backend : Spring Boot 3.2.0
    Architecture : SOA (Service Oriented Architecture)
    Base de Données : Microsoft SQL Server
    ORM / Persistance : JPA / Hibernate
    Communication : API REST (Échange de données au format JSON)
    Build Tool : Maven

✨ Fonctionnalités Principales
👨‍🎓 Espace Étudiant

L'étudiant dispose de deux scénarios pour décrocher son PFE :

    Option A (Sujet Existant) : Consulter le catalogue des sujets proposés par les sociétés, postuler et choisir un enseignant encadrant.
    Option B (Proposition Personnelle) : Proposer son propre sujet, puis inviter un enseignant et une société à valider le projet.
    Suivi : Consultation de l'état d'avancement de la demande (En attente, Validé, Refusé).

👨‍🏫 Espace Enseignant

    Réception des notifications de demande d'encadrement.
    Validation ou Refus des sujets proposés par les étudiants.
    Consultation de la liste des étudiants encadrés.

🏢 Espace Société

    Dépôt de nouveaux sujets de PFE dans le catalogue.
    Validation ou Refus des étudiants pour les sujets proposés.
    Suivi des stagiaires acceptés.

⚙️ Prérequis

Avant de lancer le projet, assurez-vous d'avoir installé :

    Java JDK 18 (ou version supérieure)
    Microsoft SQL Server
    Maven
    Un IDE (IntelliJ IDEA recommandé)
