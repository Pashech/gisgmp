package com.example.adapter.service.impl;

import com.example.adapter.model.InfoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class InfoRequestImplTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InfoRequestImpl testSubject;

    private MockRestServiceServer mockServer;
    private InfoRequest infoRequest;

    @BeforeEach
    public void setUp(){
        mockServer = MockRestServiceServer.createServer(restTemplate);
        infoRequest = new InfoRequest("ETA123456");
    }

//    @Test
//    void sendInfoRequest() {
//        mockServer.expect(once(), requestTo("http://localhost:8081/information/request/"))
//                .andExpect(method(HttpMethod.POST))
//                .andRespond(withSuccess("ETA123456", MediaType.APPLICATION_JSON));
//
//        String result = String.valueOf(testSubject.sendInfoRequest(infoRequest));
//        System.out.println(result);
//    }

    @Test
    void sendAcknowledge() {
    }
}