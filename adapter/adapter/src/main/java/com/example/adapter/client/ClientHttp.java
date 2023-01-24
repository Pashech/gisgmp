package com.example.adapter.client;

import com.example.adapter.model.Acknowledge;
import com.example.adapter.model.InfoRequest;
import com.example.adapter.model.Penalty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ClientHttp {

    private static final String URL = "http://localhost:8081/get/penalties/";
    private static final String URL_INFORMATION_REQUEST = "http://localhost:8081/information/request/";
    private static final String ACKNOWLEDGE = "http://localhost:8081/acknowledge/";

    private final RestTemplate restTemplate;

    @Autowired
    public ClientHttp(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<InfoRequest> sendInfoRequest(InfoRequest infoRequest){
        return restTemplate.postForEntity(URL_INFORMATION_REQUEST, infoRequest, InfoRequest.class);
    }

    public ResponseEntity<List<Penalty>> getPenaltyFromSmev(String vehicleCertificate){
        return restTemplate.exchange(URL + vehicleCertificate, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
    }

    public ResponseEntity<Acknowledge> sendAcknowledge(Acknowledge acknowledge){
        return restTemplate.postForEntity(ACKNOWLEDGE, acknowledge, Acknowledge.class);
    }
}
