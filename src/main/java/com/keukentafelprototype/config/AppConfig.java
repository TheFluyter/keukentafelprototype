package com.keukentafelprototype.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

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

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(STRICT);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
