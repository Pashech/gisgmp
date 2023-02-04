package com.example.smev_gisgmp.services;

import com.example.smev_gisgmp.entity.InformationRequest;

import java.util.UUID;

public interface InformationRequestService {

    InformationRequest saveInformationRequest(InformationRequest informationRequest);
    InformationRequest getInformationRequest(String vehicleCertificate);
    void deleteInformationRequest(String vehicleCertificate);
}
