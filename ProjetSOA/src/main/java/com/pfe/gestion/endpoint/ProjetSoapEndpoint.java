package com.pfe.gestion.endpoint;

import com.pfe.gestion.entity.Projet;
import com.pfe.gestion.repository.ProjetRepository;
import com.pfe.gestion.soap.GetProjetRequest;
import com.pfe.gestion.soap.GetProjetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProjetSoapEndpoint {

    private static final String NAMESPACE_URI = "http://pfe.com/soap";

    @Autowired
    private ProjetRepository projetRepo;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProjetRequest")
    @ResponsePayload
    public GetProjetResponse getProjet(@RequestPayload GetProjetRequest request) {
        GetProjetResponse response = new GetProjetResponse();

        // Recherche dans la base SQL Server
        Projet p = projetRepo.findById(request.getId()).orElse(null);

        if (p != null) {
            response.setTitre(p.getTitre());
            response.setEtat(p.getEtat().toString());
            if (p.getEtudiant() != null) {
                response.setEtudiant(p.getEtudiant().getNom());
            } else {
                response.setEtudiant("Aucun");
            }
        } else {
            response.setTitre("INCONNU");
            response.setEtat("N/A");
            response.setEtudiant("N/A");
        }

        return response;
    }
}