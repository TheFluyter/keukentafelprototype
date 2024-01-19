package com.keukentafelprototype.collector;

import com.keukentafelprototype.config.AppConfig;
import com.keukentafelprototype.controller.ScryfallBulkCollector;
import com.keukentafelprototype.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ScryfallBulkCollector.class, AppConfig.class, CardRepository.class})
@TestPropertySource("classpath:config/test.properties")
class ScryfallBulkCollectorTest {

    @MockBean
    RestTemplate mockScryfallRestTemplate;

    @Autowired
    ScryfallBulkCollector scryfallBulkCollector;

    // TODO use H2 database
    @Test
    void testDownloadCardBulkData() throws IOException {
        String downloadUriResponse = Files.readString(Path.of("src/test/resources/input/bulk-data.json"));
        String downloadCardResponse = Files.readString(Path.of(
            "src/test/resources/input/default-cards-20230203100451.json"));

        when(mockScryfallRestTemplate.getForObject("bulk-data", String.class)).thenReturn(downloadUriResponse);
        when(mockScryfallRestTemplate.getForObject("bulk-data/e2ef41e3-5778-4bc2-af3f-78eca4dd9c23", String.class))
            .thenReturn(downloadCardResponse);

        scryfallBulkCollector.downloadAllMtgDefaultCards();
    }
}