package com.example.adapter.controller;

import com.example.adapter.AdapterApplicationTests;
import com.example.adapter.model.Penalties;
import com.example.adapter.model.Penalty;
import com.example.adapter.service.PenaltyClientService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
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

        BufferedReader reader = new BufferedReader(new FileReader("src\\test\\resources\\mock\\response\\information-request.json"));

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
    void getPenaltiesWithXmlTest() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(Penalties.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Penalties penalties = (Penalties) unmarshaller.unmarshal(new File("src\\test\\resources\\mock\\response\\information-request.xml"));

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(penalties, writer);
        String xml = writer.toString();

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