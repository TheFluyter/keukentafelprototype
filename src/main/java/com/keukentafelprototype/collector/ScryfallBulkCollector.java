package com.keukentafelprototype.collector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ScryfallBulkCollector {

    private final RestTemplate scryfallRestTemplate;
    private final ObjectMapper objectMapper;

    public ScryfallBulkCollector(RestTemplate scryfallRestTemplate, ObjectMapper objectMapper) {
        this.scryfallRestTemplate = scryfallRestTemplate;
        this.objectMapper = objectMapper;
    }

    // TODO schedule job, persist to db
    protected void downloadCardBulkData() throws JsonProcessingException {
        String defaultCards = scryfallRestTemplate.getForObject("bulk-data/" + retrieveDownloadId(), String.class);
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
