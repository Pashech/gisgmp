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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WireMockTest(httpPort = 8081)
@AutoConfigureMockMvc
class InformationControllerTest extends AdapterApplicationTests {

    @Autowired
    private Gson gson;

    @Autowired
    private InfoRequestService infoRequestService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sendInfoRequest() throws Exception {

        InfoRequest infoRequest = new InfoRequest();
        infoRequest.setVehicleCertificate("ETA123456");
        String gson1 = gson.toJson(infoRequest);

        stubFor(WireMock.post("/information/request/")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json;")
                        .withBody(gson1)
                        .withStatus(202)));

        mockMvc.perform(post("/info/request/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson1))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.vehicleCertificate").value("ETA123456"));

        WireMock.verify(1, postRequestedFor(WireMock.urlEqualTo("/information/request/")));
    }

    @Test
    void sendInfoRequestWithXml() throws Exception {

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
                        .withStatus(202)));

        mockMvc.perform(post("/info/request/")
                        .contentType(MediaType.APPLICATION_XML)
                        .content(xml))
                .andDo(print())
                .andExpect(status().isAccepted());

        WireMock.verify(1, postRequestedFor(WireMock.urlEqualTo("/information/request/")));
    }
}