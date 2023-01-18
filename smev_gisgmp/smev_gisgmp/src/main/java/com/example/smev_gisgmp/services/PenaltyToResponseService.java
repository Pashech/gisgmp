package com.example.smev_gisgmp.services;

import com.example.smev_gisgmp.entity.PenaltyToResponse;

import java.util.Optional;

public interface PenaltyToResponseService {

    Optional<PenaltyToResponse> getPenaltyToResponseById(Long id);
    void deleteAllPenalties();
}
