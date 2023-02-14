package com.keukentafelprototype.collector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keukentafelprototype.domain.MtgCard;
import com.keukentafelprototype.model.Card;
import com.keukentafelprototype.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScryfallBulkCollector {

    private final RestTemplate scryfallRestTemplate;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public ScryfallBulkCollector(RestTemplate scryfallRestTemplate, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.scryfallRestTemplate = scryfallRestTemplate;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    // TODO schedule job, persist to db
    protected void downloadCardBulkData() throws JsonProcessingException {
        String json = scryfallRestTemplate.getForObject("bulk-data/" + retrieveDownloadId(), String.class);
        List<MtgCard> mtgCards = mapJsonToMtgCards(json);
        List<Card> cards = convertToEntity(mtgCards);

        for (Card card : cards) {
            log.info("Card: {},", card);
        }
    }

    private List<MtgCard> mapJsonToMtgCards(String json) throws JsonProcessingException {
        TypeReference<List<MtgCard>> typeReference = new TypeReference<>(){};
        return objectMapper.readValue(json, typeReference);
    }

    private List<Card> convertToEntity(List<MtgCard> mtgCards) {
        return mtgCards.stream()
            .map(mtgCard -> modelMapper.map(mtgCard, Card.class))
            .collect(Collectors.toList());
    }

    private String retrieveDownloadId() throws JsonProcessingException {
        String bulkDataString = scryfallRestTemplate.getForObject("bulk-data", String.class);
        JsonNode bulkDataNode = objectMapper.readTree(bulkDataString);

        for (JsonNode dataNode : bulkDataNode.get("data")) {
            if (dataNode.get("type").asText().equals("default_cards")) {
                String downloadUri = dataNode.get("uri").asText();
                return downloadUri.substring(downloadUri.lastIndexOf("/") + 1);
            }
        }
        return "";
    }
}
