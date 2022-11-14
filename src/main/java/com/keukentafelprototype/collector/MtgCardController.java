package com.keukentafelprototype.collector;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * MTG Card Controller provides different endpoints to retrieve card information via the Scryfall API
 */
@RestController
public class MtgCardController {

    private static final String SCRYFALL_API = "https://api.scryfall.com/cards/random";

    private final RestTemplate restTemplate;

    public MtgCardController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/random")
    public String random() {
        return restTemplate.getForObject(SCRYFALL_API, String.class);
    }
}
