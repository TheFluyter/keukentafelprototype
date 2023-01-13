package com.keukentafelprototype.collector;

import com.keukentafelprototype.domain.MtgCard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * MTG Card Controller provides different endpoints to retrieve card information via the Scryfall API
 */
@RestController
public class MtgCardController {

    private final RestTemplate scryfallRestTemplate;

    public MtgCardController(RestTemplate scryfallRestTemplate) {
        this.scryfallRestTemplate = scryfallRestTemplate;
    }

    @GetMapping(value = "random")
    public MtgCard random() {
        return scryfallRestTemplate.getForObject("cards/random", MtgCard.class);
    }
}
