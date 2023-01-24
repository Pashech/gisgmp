package com.example.adapter.service.impl;

import com.example.adapter.client.ClientHttp;
import com.example.adapter.model.Acknowledge;
import com.example.adapter.model.InfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InfoRequestImpl implements InfoRequestService{

    private final ClientHttp clientHttp;

    @Override
    public ResponseEntity<InfoRequest> sendInfoRequest(InfoRequest infoRequest) {
        return clientHttp.sendInfoRequest(infoRequest);
    }

    @Override
    public ResponseEntity<Acknowledge> sendAcknowledge(Acknowledge acknowledge) {
        return clientHttp.sendAcknowledge(acknowledge);
    }
}
