package com.example.smev_gisgmp.controller;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class InformationRequestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private InformationRequest informationRequest;

    @BeforeEach
    public void prepareData() {
        informationRequest = new InformationRequest();
    }

    @Test
    void createInformationRequest() throws Exception {
        informationRequest.setVehicleCertificate("ETA123456");
        String json = objectMapper.writeValueAsString(informationRequest);
        mockMvc.perform(post("/information/request/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vehicleCertificate").value("ETA123456"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
}