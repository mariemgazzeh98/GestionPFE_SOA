package com.pfe.gestion.controller;

import com.pfe.gestion.dto.LoginRequest;
import com.pfe.gestion.dto.RegisterRequest;
import com.pfe.gestion.entity.*;
import com.pfe.gestion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private EtudiantRepository etudiantRepo;
    @Autowired private EnseignantRepository enseignantRepo;
    @Autowired private SocieteRepository societeRepo;

    @PostMapping("/login")
    public Long login(@RequestBody LoginRequest request) {


        if ("ETUDIANT".equals(request.getRole())) {
            Etudiant e = etudiantRepo.findByNomAndMotDePasse(request.getIdentifiant(), request.getMotDePasse());
            return (e != null) ? e.getId() : -1L;
        }
        else if ("ENSEIGNANT".equals(request.getRole())) {
            Enseignant p = enseignantRepo.findByNomAndMotDePasse(request.getIdentifiant(), request.getMotDePasse());
            return (p != null) ? p.getId() : -1L;
        }
        else if ("SOCIETE".equals(request.getRole())) {
            // CORRECTION ICI : Utilisation de findFirstByNom
            // Cela évite le crash si plusieurs sociétés ont le même nom dans la base.
            Societe s = societeRepo.findFirstByNom(request.getIdentifiant());
            return (s != null) ? s.getId() : -1L;
        }
        return -1L;
    }

    @PostMapping("/register")
    public Long register(@RequestBody RegisterRequest req) {
        if ("ETUDIANT".equals(req.getRole())) {
            Etudiant e = new Etudiant();
            e.setNom(req.getNom());
            e.setEmail(req.getEmail());
            e.setMotDePasse(req.getMotDePasse());
            return etudiantRepo.save(e).getId();
        }
        else if ("ENSEIGNANT".equals(req.getRole())) {
            Enseignant p = new Enseignant();
            p.setNom(req.getNom());
            p.setEmail(req.getEmail());
            p.setMotDePasse(req.getMotDePasse());
            return enseignantRepo.save(p).getId();
        }
        else if ("SOCIETE".equals(req.getRole())) {
            Societe s = new Societe();
            s.setNom(req.getNom());
            // On enregistre l'email de contact
            s.setContactEmail(req.getEmail());
            return societeRepo.save(s).getId();
        }
        return -1L;
    }
}