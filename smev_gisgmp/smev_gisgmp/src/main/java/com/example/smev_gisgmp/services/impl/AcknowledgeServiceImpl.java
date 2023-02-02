package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.AcknowledgeEntity;
import com.example.smev_gisgmp.repository.AcknowledgeRepository;
import com.example.smev_gisgmp.services.AcknowledgeService;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcknowledgeServiceImpl implements AcknowledgeService {

    private final AcknowledgeRepository acknowledgeRepository;
    private final PenaltyToResponseService penaltyToResponseRepository;

    @Override
    public AcknowledgeEntity createAcknowledge(AcknowledgeEntity acknowledge) {
        acknowledgeRepository.save(acknowledge);
        if(acknowledge.getResponseId().equals(penaltyToResponseRepository.getAllPenalties().get(0).getResponseId())){
            //penaltyToResponseRepository.deleteAll();
        }
        acknowledgeRepository.delete(acknowledge);

        return acknowledge;
    }
}
