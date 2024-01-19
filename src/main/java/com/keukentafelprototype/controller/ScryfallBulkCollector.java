package com.keukentafelprototype.controller;

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
    private final RestTemplate restTemplate;
    private final MtgCardRepository mtgCardRepository;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    public ScryfallBulkCollector(RestTemplate scryfallRestTemplate,
                                 RestTemplate restTemplate,
                                 MtgCardRepository mtgCardRepository,
                                 ObjectMapper objectMapper,
                                 ModelMapper modelMapper) {
        this.scryfallRestTemplate = scryfallRestTemplate;
        this.restTemplate = restTemplate;
        this.mtgCardRepository = mtgCardRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    public void downloadAllMtgDefaultCards() throws JsonProcessingException {
        String allDefaultMtgCardsJson = restTemplate.getForObject(retrieveDownloadId(), String.class);
        List<MtgCardDTO> mtgCardsDTO = mapJsonToMtgCardDtoList(allDefaultMtgCardsJson);
        List<MtgCard> mtgCards = convertMtgCardDtoToEntity(mtgCardsDTO);

        for (int i = 0; i < 5; i++) {
            log.info("Card: {},", mtgCards.get(i));
            mtgCardRepository.save(mtgCards.get(i));
        }
    }

    private String retrieveDownloadId() throws JsonProcessingException {
        String dataDetailsJson = scryfallRestTemplate.getForObject("bulk-data", String.class);
        JsonNode dataDetailsNode = objectMapper.readTree(dataDetailsJson);

        for (JsonNode jsonNode : dataDetailsNode.get("data")) {
            if (jsonNode.get("type").asText().equals("default_cards")) {
                return jsonNode.get("download_uri").asText();
            }
        }
        return "";
    }

    private List<MtgCardDTO> mapJsonToMtgCardDtoList(String json) throws JsonProcessingException {
        TypeReference<List<MtgCardDTO>> typeReference = new TypeReference<>(){};
        return objectMapper.readValue(json, typeReference);
    }

    private List<MtgCard> convertMtgCardDtoToEntity(List<MtgCardDTO> mtgCardDtoList) {
        return mtgCardDtoList.stream()
            .map(mtgCardDto -> modelMapper.map(mtgCardDto, MtgCard.class))
            .collect(Collectors.toList());
    }
}
