package com.example.adapter.service;

import com.example.adapter.model.Acknowledge;
import com.example.adapter.model.InfoRequest;
import org.springframework.http.ResponseEntity;

public interface InfoRequestService {

    ResponseEntity<InfoRequest> sendInfoRequest(InfoRequest infoRequest);
    ResponseEntity<Acknowledge> sendAcknowledge(Acknowledge acknowledge);
}
