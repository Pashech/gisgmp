package com.example.adapter.controller;

import com.example.adapter.model.InfoRequest;
import com.example.adapter.model.dto.PenaltyDto;
import com.example.adapter.service.PenaltyClientService;
import com.example.adapter.service.impl.InfoRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PenaltyController {

    private final PenaltyClientService penaltyClientService;
    private final InfoRequestService infoRequestService;

    @PostMapping("/info/request/")
    public ResponseEntity<InfoRequest> sendInfoRequest(@Valid @RequestBody InfoRequest infoRequest) {
        return infoRequestService.sendInfoRequest(infoRequest);
    }

    @GetMapping("/getPenalties/{vehicleCertificate}")
    public ResponseEntity<List<PenaltyDto>> getPenalties(@Valid @PathVariable("vehicleCertificate")
                                                         @Pattern(regexp = "[A-Z]{3}[0-9]{6}", message = "the certificate does not match the format, for example 'XXX111111'") String vehicleCertificate) {
        return penaltyClientService.getPenalty(vehicleCertificate);
    }
}
