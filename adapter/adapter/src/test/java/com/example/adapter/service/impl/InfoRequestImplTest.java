package com.example.adapter.service.impl;

import com.example.adapter.client.ClientHttp;
import com.example.adapter.model.InfoRequest;
import com.example.adapter.model.Penalty;
import com.example.adapter.model.dto.PenaltyDto;
import com.example.adapter.service.PenaltyClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static java.nio.file.Paths.get;

@SpringBootTest
@ActiveProfiles("test")
class InfoRequestImplTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PenaltyClientService testSubject;

    private MockRestServiceServer mockServer;


    @BeforeEach
    public void setUp(){
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

//    @Test
//    void sendInfoRequest() throws IOException {
//        byte[] responseBody = readAllBytes(get("src", "test", "resources", "mock", "response", "information-request.json"));
//        DefaultResponseCreator responseCreator = withStatus(HttpStatus.OK).body(responseBody).contentType(MediaType.APPLICATION_JSON);
//        mockServer.expect(times(1), requestTo("http://localhost:8081/get/penalties/"))
//                .andExpect(method(HttpMethod.GET)).andRespond(responseCreator);
//
//        ResponseEntity<List<PenaltyDto>> result = testSubject.getPenalty("ETA123456");
//        mockServer.verify();
//    }

    @Test
    void sendAcknowledge() {
    }
}