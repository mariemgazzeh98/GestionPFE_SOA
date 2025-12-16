package com.pfe.gestion.config;

import com.pfe.gestion.endpoint.JerseyEndpoint;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/api/jersey") // C'est ici qu'on définit l'URL de base pour Jersey
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // On enregistre notre futur endpoint Jersey
        register(JerseyEndpoint.class);
    }
}