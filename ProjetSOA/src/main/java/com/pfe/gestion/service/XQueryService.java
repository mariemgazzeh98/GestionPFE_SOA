package com.pfe.gestion.service;

import com.pfe.gestion.entity.Projet;
import com.pfe.gestion.repository.ProjetRepository;
import net.sf.saxon.s9api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class XQueryService {

    @Autowired
    private ProjetRepository projetRepo;

    // 1. Convertir la base de données en String XML
    private String generateXmlFromDb() {
        List<Projet> projets = projetRepo.findAll();
        StringBuilder xml = new StringBuilder();
        xml.append("<projets>");

        for (Projet p : projets) {
            xml.append("<projet>");
            xml.append("<titre>").append(p.getTitre()).append("</titre>");
            xml.append("<etat>").append(p.getEtat()).append("</etat>");
            if (p.getSociete() != null) {
                xml.append("<societe>").append(p.getSociete().getNom()).append("</societe>");
            } else {
                xml.append("<societe>").append(p.getNomSocieteExterne()).append("</societe>");
            }
            xml.append("</projet>");
        }

        xml.append("</projets>");
        return xml.toString();
    }

    // 2. Exécuter une requête XQuery
    public List<String> filtrerParSocieteViaXQuery(String nomSociete) {
        List<String> resultats = new ArrayList<>();
        try {
            // Création du processeur
            Processor processor = new Processor(false);
            XQueryCompiler compiler = processor.newXQueryCompiler();

            // Notre requête XQuery : Trouver les titres où la société correspond
            String query = "for $x in /projets/projet " +
                    "where $x/societe = '" + nomSociete + "' " +
                    "return $x/titre/text()";

            XQueryExecutable executable = compiler.compile(query);
            XQueryEvaluator evaluator = executable.load();

            // On charge le XML généré depuis la base
            String xmlData = generateXmlFromDb();
            DocumentBuilder builder = processor.newDocumentBuilder();
            XdmNode document = builder.build(new javax.xml.transform.stream.StreamSource(new StringReader(xmlData)));

            evaluator.setContextItem(document);

            // Exécution et récupération des résultats
            for (XdmItem item : evaluator) {
                resultats.add(item.getStringValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultats.add("Erreur XQuery : " + e.getMessage());
        }
        return resultats;
    }
}