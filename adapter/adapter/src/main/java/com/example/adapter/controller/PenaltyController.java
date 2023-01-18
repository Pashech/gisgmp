package com.example.adapter.controller;

import com.example.adapter.client.ClientHttp;
import com.example.adapter.model.InfoRequest;
import com.example.adapter.model.Penalty;
import com.example.adapter.service.PenaltyClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyClientService penaltyClientService;
    private final ClientHttp clientHttp;

    @PostMapping("/info/request/")
    public ResponseEntity<InfoRequest> sendInfoRequest(@Valid @RequestBody InfoRequest infoRequest){
        return clientHttp.sendInfoRequest(infoRequest);
    }

    @GetMapping("/getPenalties/{vehicleCertificate}")
    public ResponseEntity<List<Penalty>> getPenalties(@PathVariable("vehicleCertificate") String vehicleCertificate){
        return penaltyClientService.getPenalty(vehicleCertificate);
    }
}
