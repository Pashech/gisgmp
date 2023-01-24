package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.AcknowledgeEntity;
import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.repository.AcknowledgeRepository;
import com.example.smev_gisgmp.repository.InformationRequestRepository;
import com.example.smev_gisgmp.repository.PenaltyToResponseRepository;
import com.example.smev_gisgmp.services.AcknowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcknowledgeServiceImpl implements AcknowledgeService {

    private final AcknowledgeRepository acknowledgeRepository;
    private final PenaltyToResponseRepository penaltyToResponseRepository;
    private final InformationRequestRepository informationRequestRepository;

    @Override
    public AcknowledgeEntity createAcknowledge(AcknowledgeEntity acknowledge) {
        return acknowledgeRepository.save(acknowledge);

    }
}
