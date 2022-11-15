package com.keukentafelprototype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class AppConfig {

    private static final String SCRYFALL_API = "https://api.scryfall.com";

    @Bean
    public RestTemplate scryfallRestTemplate() {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(SCRYFALL_API);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        return restTemplate;
    }
}
