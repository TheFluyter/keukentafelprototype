package com.keukentafelprototype.controller;

import com.keukentafelprototype.domain.MtgCard;
import com.keukentafelprototype.repository.CardRepository;
import com.keukentafelprototype.model.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * MTG Card Controller provides different endpoints to retrieve card information via the Scryfall API
 */
@RestController
public class MtgCardController {

    private final RestTemplate scryfallRestTemplate;
    private final CardRepository cardRepository;

    public MtgCardController(RestTemplate scryfallRestTemplate, CardRepository cardRepository) {
        this.scryfallRestTemplate = scryfallRestTemplate;
        this.cardRepository = cardRepository;
    }

    @GetMapping("random")
    public MtgCard random() {
        return scryfallRestTemplate.getForObject("cards/random", MtgCard.class);
    }

    @GetMapping("cards")
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return new ResponseEntity<>(cards, OK);
    }

    @PostMapping("cards")
    public Card createCard(@RequestBody Card card) {
        return cardRepository.save(card);
    }
}
