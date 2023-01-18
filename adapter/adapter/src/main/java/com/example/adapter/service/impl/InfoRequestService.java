package com.example.adapter.service.impl;

import com.example.adapter.model.InfoRequest;
import org.springframework.http.ResponseEntity;

public interface InfoRequestService {

    ResponseEntity<InfoRequest> sendInfoRequest(InfoRequest infoRequest);
}
