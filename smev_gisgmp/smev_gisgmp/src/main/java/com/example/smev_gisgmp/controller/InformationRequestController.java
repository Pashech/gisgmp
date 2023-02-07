package com.example.smev_gisgmp.controller;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.services.impl.InformationRequestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class InformationRequestController {
    
    private final InformationRequestServiceImpl informationRequestService;

    @PostMapping("/information/request/")
    public ResponseEntity<InformationRequest> createInformationRequest(@Valid @RequestBody InformationRequest informationRequest){
        return new ResponseEntity<>(informationRequestService.saveInformationRequest(informationRequest), HttpStatus.CREATED);
    }
}
