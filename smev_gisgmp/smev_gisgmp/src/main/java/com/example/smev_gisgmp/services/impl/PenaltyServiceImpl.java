package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.exception_handling.NoPenaltyException;
import com.example.smev_gisgmp.repository.InformationRequestRepository;
import com.example.smev_gisgmp.repository.PenaltyRepository;
import com.example.smev_gisgmp.repository.PenaltyToResponseRepository;
import com.example.smev_gisgmp.services.PenaltyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltyServiceImpl implements PenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final InformationRequestRepository informationRequestRepository;
    private final PenaltyToResponseRepository penaltyToResponseRepository;

    @Override
    public List<PenaltyToResponse> getPenalty(String vehicleCertificate) {

        List<PenaltyToResponse> penalties1 = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                log.info("Worker has started!!!");
                InformationRequest informationRequest = informationRequestRepository.getInformationRequestByVehicleCertificate(vehicleCertificate);
                log.info("Request ID : " + informationRequest.getId());
                List<Penalty> penalties = penaltyRepository.getPenaltiesByVehicleCertificate(informationRequest.getVehicleCertificate());
                List<PenaltyToResponse> penaltyToResponseList = new ArrayList<>();
                for (Penalty penalty : penalties) {
                    penaltyToResponseList.add(change(penalty));
                }
                for(PenaltyToResponse p : penaltyToResponseList){
                    p.setResponseId(informationRequest.getId());
                }
                penaltyToResponseRepository.saveAll(penaltyToResponseList);
                informationRequestRepository.delete(informationRequest);
            } catch (NullPointerException e) {
                throw new NoPenaltyException("Penalty haven`t found yet, please, try to send request later");
            }
        });
        thread.start();

        int simulatorChance = (int) (Math.random() * 10);
        log.info(String.valueOf(simulatorChance));
        if (simulatorChance != 1 && simulatorChance != 2 && simulatorChance != 3) {
            penalties1 = penaltyToResponseRepository.findAll();
        }
        return penalties1;
    }

    public static PenaltyToResponse change(Penalty penalty) {
        PenaltyToResponse penaltyToResponse = new PenaltyToResponse();
        penaltyToResponse.setAccruedAmount(penalty.getAccruedAmount());
        penaltyToResponse.setVehicleCertificate(penalty.getVehicleCertificate());
        penaltyToResponse.setResolutionNumber(penalty.getResolutionNumber());
        penaltyToResponse.setDecisionDate(penalty.getDecisionDate());
        penaltyToResponse.setArticleKoAP(penalty.getArticleKoAP());
        penaltyToResponse.setAmountPayable(penalty.getAmountPayable());
        return penaltyToResponse;
    }
}
