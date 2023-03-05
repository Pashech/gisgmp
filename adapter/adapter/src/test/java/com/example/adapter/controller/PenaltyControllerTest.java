package com.example.adapter.controller;

import com.example.adapter.AdapterApplicationTests;
import com.example.adapter.model.Acknowledge;
import com.example.adapter.model.Penalties;
import com.example.adapter.model.Penalty;
import com.example.adapter.model.dto.PenaltyDto;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

//    @Test
//    void getPenaltiesWithXmlTest() throws Exception {
//
//        File file = new File("src\\test\\resources\\mock\\response\\information-request.xml");
//        JAXBContext context = JAXBContext.newInstance(Penalties.class);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        Penalties penalties = (Penalties) unmarshaller.unmarshal(file);
//        System.out.println(penalties);
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        marshaller.marshal(penalties, new File("src\\test\\resources\\mock\\response\\information-requests.xml"));
//        File file1 = new File("src\\test\\resources\\mock\\response\\information-requests.xml");
//
//
//        stubFor(WireMock.get("/get/penalties/ETA123456")
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/xml;")
//                        .withBody(String.valueOf(file1))
//                        .withStatus(200)));
//
//        stubFor(WireMock.post("/acknowledge/")
//                .willReturn(aResponse()
//                        .withStatus(200)));
//
//        penaltyClientService.getPenalty("ETA123456");
//
//        WireMock.verify(1, getRequestedFor(WireMock.urlEqualTo("/get/penalties/ETA123456")));
//        WireMock.verify(1, postRequestedFor(WireMock.urlEqualTo("/acknowledge/")));
//
//    }

    @Test
    void xmlTest() throws JAXBException {

        //String st = "D:\\Intervale\\gisgmp\\adapter\\adapter\\src\\test\\resources\\mock\\response\\information-request.xml";

        File file = new File("src\\test\\resources\\mock\\response\\information-request.xml");
        JAXBContext context = JAXBContext.newInstance(Penalties.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Penalties penalties = (Penalties) unmarshaller.unmarshal(file);
        System.out.println(penalties);
    }
}