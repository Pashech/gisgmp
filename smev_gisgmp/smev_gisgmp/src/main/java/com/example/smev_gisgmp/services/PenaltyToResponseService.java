package com.example.smev_gisgmp.services;

import com.example.smev_gisgmp.entity.PenaltyToResponse;

import java.util.List;

public interface PenaltyToResponseService {

    List<PenaltyToResponse> getAllPenalties();
    void deleteAllPenalties();

    int savePenaltyToResponse(PenaltyToResponse penaltyToResponse);
}
