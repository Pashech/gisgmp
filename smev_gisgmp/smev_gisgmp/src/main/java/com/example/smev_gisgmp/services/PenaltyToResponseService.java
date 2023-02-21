package com.example.smev_gisgmp.services;

import com.example.smev_gisgmp.entity.PenaltyToResponse;

import java.util.List;
import java.util.UUID;

public interface PenaltyToResponseService {

    List<PenaltyToResponse> getAllPenalties();

    void deletePenaltiesByResponseId(UUID uuid);

    int savePenaltyToResponse(PenaltyToResponse penaltyToResponse);
}
