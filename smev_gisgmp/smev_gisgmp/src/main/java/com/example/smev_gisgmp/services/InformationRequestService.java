package com.example.smev_gisgmp.services;

import com.example.smev_gisgmp.entity.InformationRequest;

import java.util.Optional;

public interface InformationRequestService {

    InformationRequest getInformationRequestByVehicleCertificate(String vehicleCertificate);
    InformationRequest saveInformationRequest(InformationRequest informationRequest);
    Optional<InformationRequest> getInformationRequest();
    void deleteInformationRequest(String vehicleCertificate);
}
