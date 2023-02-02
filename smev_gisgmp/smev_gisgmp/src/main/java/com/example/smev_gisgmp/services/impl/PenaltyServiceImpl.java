package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.repository.PenaltyRepository;
import com.example.smev_gisgmp.services.InformationRequestService;
import com.example.smev_gisgmp.services.PenaltyService;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltyServiceImpl implements PenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final InformationRequestService informationRequestService;
    private final PenaltyToResponseService penaltyToResponseRepository;

    @Override
    public List<PenaltyToResponse> getPenalty(String vehicleCertificate) {

        List<PenaltyToResponse> penaltyToResponseList1 = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                log.info("Worker has started!!!");
                InformationRequest informationRequest = informationRequestService.getInformationRequest(vehicleCertificate);
                log.info(informationRequest + "from impl");
                log.info("Request ID : " + informationRequest.getId());
                List<Penalty> penalties = penaltyRepository.getPenaltiesByVehicleCertificate(informationRequest.getVehicleCertificate());
                log.info(penalties + " from penalties");
//                List<PenaltyToResponse> penaltyToResponseList = new ArrayList<>();
//                for (Penalty penalty : penalties) {
//                    penaltyToResponseList.add(changePenaltyToPenaltyResponse(penalty));
//                }
//                for (PenaltyToResponse p : penaltyToResponseList) {
//                    p.setResponseId(informationRequest.getId());
//                }
//                List<PenaltyToResponse> all = penaltyToResponseRepository.findAll();
//                if (all.size() == 0) {
//                    penaltyToResponseRepository.saveAll(penaltyToResponseList);
//                    //informationRequestRepository.delete(informationRequest.get());
//                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("server out of order");
            }
        });

        thread.start();

//        penaltyToResponseList1 = penaltyToResponseRepository.getAllPenalties();
//        int simulatorChance = (int) (Math.random() * 10);
//        log.info(String.valueOf(simulatorChance));
//        if (simulatorChance == 1 || simulatorChance == 2 || simulatorChance == 3 || simulatorChance == 4) {
//            penaltyToResponseList1 = penaltyToResponseRepository.getAllPenalties();
//        }
        return penaltyToResponseList1;
    }

//    public static PenaltyToResponse changePenaltyToPenaltyResponse(Penalty penalty) {
//        PenaltyToResponse penaltyToResponse = new PenaltyToResponse();
//        penaltyToResponse.setAccruedAmount(penalty.getAccruedAmount());
//        penaltyToResponse.setVehicleCertificate(penalty.getVehicleCertificate());
//        penaltyToResponse.setResolutionNumber(penalty.getResolutionNumber());
//        penaltyToResponse.setDecisionDate(penalty.getDecisionDate());
//        penaltyToResponse.setArticleKoAP(penalty.getArticleKoAP());
//        penaltyToResponse.setAmountPayable(penalty.getAmountPayable());
//        return penaltyToResponse;
//    }
}
