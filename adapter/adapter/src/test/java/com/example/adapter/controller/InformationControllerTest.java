package com.example.adapter.controller;

import com.example.adapter.AdapterApplicationTests;
import com.example.adapter.model.InfoRequest;
import com.example.adapter.service.InfoRequestService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@WireMockTest(httpPort = 8081)
class InformationControllerTest extends AdapterApplicationTests {

    @Autowired
    private Gson gson;

    @Autowired
    private InfoRequestService infoRequestService;

    @Test
    void sendInfoRequest() {

        InfoRequest infoRequest = new InfoRequest();
        String gson1 = gson.toJson(infoRequest);
        infoRequest.setVehicleCertificate("ETA123456");

        stubFor(WireMock.post("/information/request/")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json;")
                        .withBody(gson1)
                        .withStatus(200)));

        infoRequestService.sendInfoRequest(infoRequest);

        WireMock.verify(1, postRequestedFor(WireMock.urlEqualTo("/information/request/")));
    }
}