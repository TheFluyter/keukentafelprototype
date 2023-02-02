package com.keukentafelprototype.controller;

import com.keukentafelprototype.domain.MtgCard;
import com.keukentafelprototype.dto.CardDTO;
import com.keukentafelprototype.exception.ResourceNotFoundException;
import com.keukentafelprototype.model.Card;
import com.keukentafelprototype.repository.CardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;

/**
 * MTG Card Controller provides different endpoints to retrieve card information via the Scryfall API
 */
@RestController
public class MtgCardController {

    private final RestTemplate scryfallRestTemplate;
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    public MtgCardController(RestTemplate scryfallRestTemplate, CardRepository cardRepository, ModelMapper modelMapper) {
        this.scryfallRestTemplate = scryfallRestTemplate;
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("random")
    public MtgCard random() {
        return scryfallRestTemplate.getForObject("cards/random", MtgCard.class);
    }

    @GetMapping("cards")
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("cards/{name}")
    public ResponseEntity<List<Card>> getCardsByName(@PathVariable(value = "name") String name) {
        List<Card> cards = cardRepository.findByNameContaining(name);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("sets/{setname}")
    public ResponseEntity<List<Card>> getCardsBySetName(@PathVariable(value = "setname") String setName) {
        List<Card> cards = cardRepository.findBySetNameContaining(setName);
        return ResponseEntity.ok(cards);
    }

    @PostMapping("cards")
    public ResponseEntity<Card> createCard(@Valid @RequestBody CardDTO cardDTO) {
        Card card = modelMapper.map(cardDTO, Card.class);
        return ResponseEntity.ok(cardRepository.save(card));
    }

    @DeleteMapping("cards/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCard(@PathVariable(value = "id") Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
            new ResourceNotFoundException("Card not found for this id: " + cardId));
        cardRepository.delete(card);

        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", TRUE);
        return ResponseEntity.ok(response);
    }
}
