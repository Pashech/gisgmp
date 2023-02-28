package com.example.adapter.controller;

import com.example.adapter.model.InfoRequest;
import com.example.adapter.service.InfoRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class InformationController {

    private final InfoRequestService infoRequestService;

    @PostMapping("/info/request/")
    public ResponseEntity<InfoRequest> sendInfoRequest(@Valid @RequestBody InfoRequest infoRequest) {
        return infoRequestService.sendInfoRequest(infoRequest);
    }
}
