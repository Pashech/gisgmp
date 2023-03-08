package com.example.adapter.controller;

import com.example.adapter.AdapterApplicationTests;
import com.example.adapter.model.InfoRequest;
import com.example.adapter.model.Penalties;
import com.example.adapter.service.InfoRequestService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;

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
                        .withStatus(202)));

        infoRequestService.sendInfoRequest(infoRequest);

        WireMock.verify(1, postRequestedFor(WireMock.urlEqualTo("/information/request/")));
    }

    @Test
    void sendInfoRequestWithXml() throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(InfoRequest.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InfoRequest infoRequest = (InfoRequest) unmarshaller.unmarshal(new File("src\\test\\resources\\mock\\response\\request.xml"));

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(infoRequest, writer);
        String xml = writer.toString();

        stubFor(WireMock.post("/information/request/")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/xml;")
                        .withBody(xml)
                        .withStatus(200)));

        infoRequestService.sendInfoRequest(infoRequest);

        WireMock.verify(1, postRequestedFor(WireMock.urlEqualTo("/information/request/")));
    }
}