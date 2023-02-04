package com.example.smev_gisgmp.services;

import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;

import java.util.List;

public interface PenaltyService {

    List<PenaltyToResponse> getPenalty(String vehicleCertificate) throws InterruptedException;
    List<Penalty> getPenaltiesByVehicleCertificate(String vehicleCertificate);

}
