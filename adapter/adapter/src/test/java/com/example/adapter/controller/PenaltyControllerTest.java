package com.example.adapter.controller;

import com.example.adapter.AdapterApplicationTests;
import com.example.adapter.model.Penalty;
import com.example.adapter.service.PenaltyClientService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@WireMockTest(httpPort = 8081)
class PenaltyControllerTest extends AdapterApplicationTests {

    @Autowired
    private Gson gson;

    @Autowired
    private PenaltyClientService penaltyClientService;

    @Test
    void getPenaltiesWithJsonTest() throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader("src\\test\\resources\\mock\\response\\" +
                "information-request.json"));

        List<Penalty> penalty = gson.fromJson(reader, List.class);
        String gsonPenalty = gson.toJson(penalty);
        System.out.println(penalty);

        stubFor(WireMock.get("/get/penalties/ETA123456")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json;")
                        .withBody(gsonPenalty)
                        .withStatus(200)));

        stubFor(WireMock.post("/acknowledge/")
                .willReturn(aResponse()
                        .withStatus(200)));

            penaltyClientService.getPenalty("ETA123456");

        WireMock.verify(1, getRequestedFor(WireMock.urlEqualTo("/get/penalties/ETA123456")));
        WireMock.verify(1, postRequestedFor(WireMock.urlEqualTo("/acknowledge/")));

    }

    @Test
    void getPenaltiesWithXmlTest() {

        String xml =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                        + "<penalties>\n"
                        + "     <penalty>\n"
                        + "         <accruedAmount>100</accruedAmount>\n"
                        + "         <amountPayable>100</amountPayable>\n"
                        + "         <resolutionNumber>123</resolutionNumber>\n"
                        + "         <vehicleCertificate>ETA123456</vehicleCertificate>\n"
                        + "         <decisionDate>2023-01-02</decisionDate>\n"
                        + "         <articleKoAP>234p5</articleKoAP>\n"
                        + "     </penalty>\n"
                        + "     <penalty>\n"
                        + "         <accruedAmount>200</accruedAmount>\n"
                        + "         <amountPayable>200</amountPayable>\n"
                        + "         <resolutionNumber>123</resolutionNumber>\n"
                        + "         <vehicleCertificate>ETA123456</vehicleCertificate>\n"
                        + "         <decisionDate>2023-01-02</decisionDate>\n"
                        + "         <articleKoAP>234p5</articleKoAP>\n"
                        + "     </penalty>\n"
                        + "</penalties>";

        stubFor(WireMock.get("/get/penalties/ETA123456")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/xml;")
                        .withBody(xml)
                        .withStatus(200)));

        stubFor(WireMock.post("/acknowledge/")
                .willReturn(aResponse()
                        .withStatus(200)));

        penaltyClientService.getPenalty("ETA123456");

        WireMock.verify(1, getRequestedFor(WireMock.urlEqualTo("/get/penalties/ETA123456")));
        WireMock.verify(1, postRequestedFor(WireMock.urlEqualTo("/acknowledge/")));

    }
}