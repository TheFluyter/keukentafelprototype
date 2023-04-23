package com.keukentafelprototype.collector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keukentafelprototype.domain.MtgCardDTO;
import com.keukentafelprototype.model.MtgCard;
import com.keukentafelprototype.repository.MtgCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScryfallBulkCollector {

    private final RestTemplate scryfallRestTemplate;
    private final MtgCardRepository mtgCardRepository;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public ScryfallBulkCollector(RestTemplate scryfallRestTemplate,
                                 MtgCardRepository mtgCardRepository,
                                 ObjectMapper objectMapper,
                                 ModelMapper modelMapper) {
        this.scryfallRestTemplate = scryfallRestTemplate;
        this.mtgCardRepository = mtgCardRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    public void downloadCardBulkData() throws JsonProcessingException {
        String json = scryfallRestTemplate.getForObject("bulk-data/", String.class);
        log.info("Retrieved following json: {}", json);
        String downloadUri = retrieveDownloadId();
        log.info("Download URI: {}", downloadUri);
        RestTemplate restTemplate = new RestTemplate();
        String bulkDataJson = restTemplate.getForObject(downloadUri, String.class);
        log.info("Download bulk data");

        List<MtgCardDTO> mtgCardsDTO = mapJsonToMtgCards(bulkDataJson);
        log.info("Mapping from json to MTG Cards DTO list successful");
        List<MtgCard> mtgCards = convertToEntity(mtgCardsDTO);
        log.info("Mapping from MTG Card DTO List to MTG Card List successful");

        for (int i = 0; i < 5; i++) {
            log.info("MTG Card: {}", mtgCardsDTO.get(i));
            log.info("Card: {},", mtgCards.get(i));
            mtgCardRepository.save(mtgCards.get(i));
        }
    }

    private List<MtgCardDTO> mapJsonToMtgCards(String json) throws JsonProcessingException {
        TypeReference<List<MtgCardDTO>> typeReference = new TypeReference<>(){};
        return objectMapper.readValue(json, typeReference);
    }

    private List<MtgCard> convertToEntity(List<MtgCardDTO> mtgCardsDTO) {
        return mtgCardsDTO.stream()
            .map(mtgCard -> modelMapper.map(mtgCard, MtgCard.class))
            .collect(Collectors.toList());
    }

    private String retrieveDownloadId() throws JsonProcessingException {
        String bulkDataString = scryfallRestTemplate.getForObject("bulk-data", String.class);
        JsonNode bulkDataNode = objectMapper.readTree(bulkDataString);

        for (JsonNode dataNode : bulkDataNode.get("data")) {
            if (dataNode.get("type").asText().equals("default_cards")) {
                return dataNode.get("download_uri").asText();
            }
        }
        return "";
    }
}
