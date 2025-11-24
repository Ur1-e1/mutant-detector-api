package com.magneto.mutant_detector.controller;

import com.magneto.mutant_detector.dto.DnaRequest;
import com.magneto.mutant_detector.dto.StatsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MutantControllerIntegrationTest {

    private int port;

    private TestRestTemplate restTemplate;

    @Autowired
    public MutantControllerIntegrationTest(TestRestTemplate restTemplate, @LocalServerPort int port) {
        this.restTemplate = restTemplate;
        this.port = port;
    }

    @BeforeEach
    public void setup() {

        assertNotEquals(0, port);
        assertNotNull(restTemplate);
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void postMutant_returns200_whenMutant() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

        DnaRequest request = new DnaRequest(dna);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DnaRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl() + "/mutant", entity, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void postMutant_returns403_whenHuman() {
        String[] dna = {"ATGC","CAGT","TTAT","AGAA"};
        DnaRequest request = new DnaRequest(dna);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DnaRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl() + "/mutant", entity, Void.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getStats_returnsCounts() {

        postMutant_returns200_whenMutant();
        postMutant_returns403_whenHuman();

        ResponseEntity<StatsResponse> response = restTemplate.getForEntity(baseUrl() + "/stats", StatsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StatsResponse stats = response.getBody();
        assertNotNull(stats);
        assertEquals(1L, stats.countMutantDna());
        assertEquals(1L, stats.countHumanDna());
        assertEquals(1.0, stats.ratio());
    }
}
