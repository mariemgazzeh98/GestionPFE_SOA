package com.pfe.gestion.endpoint;

import com.pfe.gestion.entity.Etudiant;
import com.pfe.gestion.repository.EtudiantRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // Important pour que Spring puisse injecter le Repository
@Path("/admin") // L'URL sera /api/jersey/admin
public class JerseyEndpoint {

    @Autowired
    private EtudiantRepository etudiantRepo;

    // 1. Test simple : Hello Jersey
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String direBonjour() {
        return "Bonjour ! Ceci est un service JAX-RS généré avec Jersey (Technologie Servlet).";
    }

    // 2. Lister les étudiants (Format JSON)
    @GET
    @Path("/etudiants")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepo.findAll();
    }

    // 3. Chercher un étudiant par ID
    @GET
    @Path("/etudiants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Etudiant getEtudiant(@PathParam("id") Long id) {
        return etudiantRepo.findById(id).orElse(null);
    }
}