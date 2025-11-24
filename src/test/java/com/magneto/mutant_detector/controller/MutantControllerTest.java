package com.magneto.mutant_detector.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magneto.mutant_detector.dto.DnaRequest;
import com.magneto.mutant_detector.service.MutantService;
import com.magneto.mutant_detector.service.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MutantController.class)
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void postMutant_returns200_whenMutant() throws Exception {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        DnaRequest request = new DnaRequest(dna);

        when(mutantService.isMutant(any())).thenReturn(true);

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void postMutant_returns403_whenHuman() throws Exception {
        String[] dna = {"ATGC","CAGT","TTAT","AGAA"};
        DnaRequest request = new DnaRequest(dna);

        when(mutantService.isMutant(any())).thenReturn(false);

        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getStats_returns200() throws Exception {
        mockMvc.perform(get("/stats")).andExpect(status().isOk());
    }
}

