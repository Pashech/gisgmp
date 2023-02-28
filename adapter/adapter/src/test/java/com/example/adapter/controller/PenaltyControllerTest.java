package com.example.adapter.controller;

import com.example.adapter.AdapterApplicationTests;
import com.example.adapter.model.Penalty;
import com.example.adapter.service.PenaltyClientService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.FileReader;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@WireMockTest(httpPort = 8081)
class PenaltyControllerTest extends AdapterApplicationTests {

    @Autowired
    private Gson gson;

    @Autowired
    private PenaltyClientService penaltyClientService;

    @Test
    void getPenaltiesTest() throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader("D:\\Intervale\\gisgmp\\adapter\\adapter\\src\\test\\resources\\mock\\response\\" +
                "information-request.json"));

        Penalty penalty = gson.fromJson(reader, Penalty.class);
        String gsonPenalty = gson.toJson(penalty);
        System.out.println(penalty);

        stubFor(WireMock.get("/get/penalties/ETA123456")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json;")
                        .withBody(gsonPenalty)
                        .withStatus(200)));

        try{
            penaltyClientService.getPenalty("ETA123456");
        }catch (Exception e){

        }

        WireMock.verify(1, getRequestedFor(WireMock.urlEqualTo("/get/penalties/ETA123456")));

    }
}