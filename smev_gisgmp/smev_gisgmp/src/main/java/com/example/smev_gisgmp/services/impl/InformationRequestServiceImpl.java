package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.repository.InformationRequestRepository;
import com.example.smev_gisgmp.services.InformationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InformationRequestServiceImpl implements InformationRequestService {

    private final InformationRequestRepository informationRequestRepository;

    @Override
    public InformationRequest saveInformationRequest(InformationRequest informationRequest) {
        Optional<InformationRequest> informationRequestFromQueue = informationRequestRepository.getInformationRequestByVehicleCertificate(informationRequest.getVehicleCertificate());
        if(informationRequestFromQueue.isEmpty()){
            informationRequestRepository.save(informationRequest);
        }
        return informationRequest;
    }
}
