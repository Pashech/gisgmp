package com.example.adapter.controller;

import com.example.adapter.model.InfoRequest;
import com.example.adapter.model.dto.PenaltyDto;
import com.example.adapter.service.PenaltyClientService;
import com.example.adapter.service.impl.InfoRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyClientService penaltyClientService;
    private final InfoRequestService infoRequestService;

    @PostMapping("/info/request/")
    public ResponseEntity<InfoRequest> sendInfoRequest(@RequestBody InfoRequest infoRequest){
        return infoRequestService.sendInfoRequest(infoRequest);
    }

    @GetMapping("/getPenalties/{vehicleCertificate}")
    public ResponseEntity<List<PenaltyDto>> getPenalties(@PathVariable("vehicleCertificate") String vehicleCertificate){
        return penaltyClientService.getPenalty(vehicleCertificate);
    }
}
