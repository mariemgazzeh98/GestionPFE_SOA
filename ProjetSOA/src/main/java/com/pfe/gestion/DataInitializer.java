package com.pfe.gestion;

import com.pfe.gestion.entity.*;
import com.pfe.gestion.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private SocieteRepository societeRepo;
    @Autowired private EnseignantRepository profRepo;
    @Autowired private EtudiantRepository etudiantRepo;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- APPLICATION DÉMARRÉE (Aucune donnée de test n'a été ajoutée) ---");
    }
}