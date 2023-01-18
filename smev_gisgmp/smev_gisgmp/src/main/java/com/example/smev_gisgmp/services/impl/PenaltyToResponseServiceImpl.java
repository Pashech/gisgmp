package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.repository.PenaltyToResponseRepository;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PenaltyToResponseServiceImpl implements PenaltyToResponseService {

    private final PenaltyToResponseRepository penaltyToResponseRepository;

    @Override
    public Optional<PenaltyToResponse> getPenaltyToResponseById(Long id) {
        return penaltyToResponseRepository.findById(id);
    }

    @Override
    public void deleteAllPenalties() {
        penaltyToResponseRepository.deleteAll();
    }
}
