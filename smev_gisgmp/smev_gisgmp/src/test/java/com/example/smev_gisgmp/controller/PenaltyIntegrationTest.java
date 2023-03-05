package com.example.smev_gisgmp.controller;

import com.example.smev_gisgmp.SmevGisgmpApplicationTests;
import com.example.smev_gisgmp.entity.AcknowledgeEntity;
import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.services.InformationRequestService;
import com.example.smev_gisgmp.worker.Worker;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.example.smev_gisgmp.constants.Constants.INSERT_PENALTIES_TEST;
import static com.example.smev_gisgmp.constants.Constants.INSERT_PENALTY_TO_RESPONSE_TEST;
import static com.example.smev_gisgmp.constants.Constants.SELECT_PENALTY_TO_RESPONSE_TEST;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
class PenaltyIntegrationTest extends SmevGisgmpApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InformationRequestService informationRequestService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Worker worker;

    private final InformationRequest informationRequest = new InformationRequest();
    private final InformationRequest informationRequest2 = new InformationRequest();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private PenaltyToResponse penalty;
    private PenaltyToResponse penalty2;
    private PenaltyToResponse penalty3;

    @BeforeEach
    public void prepareData() {

        informationRequest.setId(UUID.randomUUID());
        informationRequest2.setId(UUID.randomUUID());
        informationRequest.setVehicleCertificate("ETA123456");

        penalty = new PenaltyToResponse();
        penalty.setId(UUID.randomUUID());
        penalty.setAccruedAmount(BigDecimal.valueOf(30));
        penalty.setAmountPayable(BigDecimal.valueOf(30));
        penalty.setArticleKoAP("24.p9");
        penalty.setDecisionDate(LocalDate.now());
        penalty.setResolutionNumber(1234);
        penalty.setVehicleCertificate("ETA123456");
        penalty.setResponseId(informationRequest.getId());

        penalty2 = new PenaltyToResponse();
        penalty2.setId(UUID.randomUUID());
        penalty2.setAccruedAmount(BigDecimal.valueOf(70));
        penalty2.setAmountPayable(BigDecimal.valueOf(70));
        penalty2.setArticleKoAP("30.p9");
        penalty2.setDecisionDate(LocalDate.now());
        penalty2.setResolutionNumber(4444);
        penalty2.setVehicleCertificate("ETA123456");
        penalty2.setResponseId(informationRequest.getId());

        penalty3 = new PenaltyToResponse();
        penalty3.setId(UUID.randomUUID());
        penalty3.setAccruedAmount(BigDecimal.valueOf(100));
        penalty3.setAmountPayable(BigDecimal.valueOf(100));
        penalty3.setArticleKoAP("30.p9");
        penalty3.setDecisionDate(LocalDate.now());
        penalty3.setResolutionNumber(5555);
        penalty3.setVehicleCertificate("ETA987654");
        penalty3.setResponseId(informationRequest2.getId());
    }

    @Test
    @Sql(value = "classpath:reset/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    void getPenalties() throws Exception {
        informationRequestService.saveInformationRequest(informationRequest);
        insertPenalty(penalty);
        insertPenalty(penalty2);
        insertPenalty(penalty3);
        worker.run();

        mockMvc.perform(get("/get/penalties/" + informationRequest.getVehicleCertificate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].vehicleCertificate").value(informationRequest.getVehicleCertificate()))
                .andExpect(jsonPath("$[1].vehicleCertificate").value(informationRequest.getVehicleCertificate()));
    }

    @Test
    @Sql(value = "classpath:reset/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    void getPenaltiesWithNotFound() throws Exception {
        informationRequestService.saveInformationRequest(informationRequest);
        worker.run();

        mockMvc.perform(get("/get/penalties/" + informationRequest.getVehicleCertificate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Sql(value = "classpath:reset/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    public void createAcknowledgeTest() throws Exception {
        AcknowledgeEntity acknowledge = new AcknowledgeEntity();
        acknowledge.setResponseId(informationRequest.getId());
        insertPenaltyToPenaltyToResponse(penalty);
        insertPenaltyToPenaltyToResponse(penalty2);
        insertPenaltyToPenaltyToResponse(penalty3);

        String json = objectMapper.writeValueAsString(acknowledge);
        log.info(json);
        log.info(informationRequest.getId().toString());

        List<PenaltyToResponse> penalties = jdbcTemplate.query(SELECT_PENALTY_TO_RESPONSE_TEST, new BeanPropertyRowMapper<>(PenaltyToResponse.class));
        assertEquals(3, penalties.size());

        mockMvc.perform(post("/acknowledge/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        List<PenaltyToResponse> penaltiesAfterAcknowledge = jdbcTemplate.query(SELECT_PENALTY_TO_RESPONSE_TEST, new BeanPropertyRowMapper<>(PenaltyToResponse.class));
        assertEquals(1, penaltiesAfterAcknowledge.size());
    }

    private void insertPenalty(PenaltyToResponse penalty) {
        jdbcTemplate.update(INSERT_PENALTIES_TEST,
                penalty.getId(),
                penalty.getAccruedAmount(),
                penalty.getAmountPayable(),
                penalty.getResolutionNumber(),
                penalty.getVehicleCertificate(),
                penalty.getDecisionDate(),
                penalty.getArticleKoAP()
        );
    }

    private void insertPenaltyToPenaltyToResponse(PenaltyToResponse penalty) {
        jdbcTemplate.update(INSERT_PENALTY_TO_RESPONSE_TEST,
                penalty.getId(),
                penalty.getAccruedAmount(),
                penalty.getAmountPayable(),
                penalty.getResolutionNumber(),
                penalty.getVehicleCertificate(),
                penalty.getDecisionDate(),
                penalty.getArticleKoAP(),
                penalty.getResponseId());
    }
}
