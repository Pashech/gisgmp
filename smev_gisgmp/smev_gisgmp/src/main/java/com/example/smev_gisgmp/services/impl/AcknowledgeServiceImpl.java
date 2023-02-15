package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.AcknowledgeEntity;
import com.example.smev_gisgmp.services.AcknowledgeService;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcknowledgeServiceImpl implements AcknowledgeService {

    private final PenaltyToResponseService penaltyToResponseService;

    @Override
    public AcknowledgeEntity createAcknowledge(AcknowledgeEntity acknowledge) {
        if(acknowledge.getResponseId().equals(penaltyToResponseService.getAllPenalties().get(0).getResponseId())){
            penaltyToResponseService.deletePenaltiesByResponseId(acknowledge.getResponseId());
        }
        return acknowledge;
    }
}
