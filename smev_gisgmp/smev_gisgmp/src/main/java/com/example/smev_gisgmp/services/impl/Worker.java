//package com.example.smev_gisgmp.services.impl;
//
//import com.example.smev_gisgmp.entity.InformationRequest;
//import com.example.smev_gisgmp.entity.Penalty;
//import com.example.smev_gisgmp.entity.PenaltyToResponse;
//import com.example.smev_gisgmp.exception_handling.InformationRequestException;
//import com.example.smev_gisgmp.repository.InformationRequestRepository;
//import com.example.smev_gisgmp.repository.PenaltyRepository;
//import com.example.smev_gisgmp.repository.PenaltyToResponseRepository;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@RequiredArgsConstructor
//public class Worker extends Thread {
//
//    private final PenaltyRepository penaltyRepository;
//    private final InformationRequestRepository informationRequestRepository;
//    private final PenaltyToResponseRepository penaltyToResponseRepository;
//    private String vehicleCertificate;
//
//    @Override
//    public void run() {
//        try {
//            log.info("Worker has started!!!");
//            InformationRequest informationRequest = getInformationRequestFromDb(vehicleCertificate);
//            log.info("Request ID : " + informationRequest.getId());
//            List<Penalty> penalties = penaltyRepository.getPenaltiesByVehicleCertificate(informationRequest.getVehicleCertificate());
//            List<PenaltyToResponse> penaltyToResponseList = new ArrayList<>();
//            for (Penalty penalty : penalties) {
//                penaltyToResponseList.add(changePenaltyToPenaltyResponse(penalty));
//            }
//            for (PenaltyToResponse p : penaltyToResponseList) {
//                p.setResponseId(informationRequest.getId());
//            }
//            List<PenaltyToResponse> all = penaltyToResponseRepository.findAll();
//            if (all.size() == 0) {
//                penaltyToResponseRepository.saveAll(penaltyToResponseList);
//                informationRequestRepository.delete(informationRequest);
//            }
//        } catch (NullPointerException e) {
//            throw new IllegalArgumentException("server out of order");
//        }
//    }
//
//    private InformationRequest getInformationRequestFromDb(String vehicleCertificate){
//       return Optional.ofNullable(informationRequestRepository.getInformationRequestByVehicleCertificate(vehicleCertificate)).get()
//               .orElseThrow(() -> new InformationRequestException("Request not found"));
//    }
//
//    private static PenaltyToResponse changePenaltyToPenaltyResponse(Penalty penalty) {
//        PenaltyToResponse penaltyToResponse = new PenaltyToResponse();
//        penaltyToResponse.setAccruedAmount(penalty.getAccruedAmount());
//        penaltyToResponse.setVehicleCertificate(penalty.getVehicleCertificate());
//        penaltyToResponse.setResolutionNumber(penalty.getResolutionNumber());
//        penaltyToResponse.setDecisionDate(penalty.getDecisionDate());
//        penaltyToResponse.setArticleKoAP(penalty.getArticleKoAP());
//        penaltyToResponse.setAmountPayable(penalty.getAmountPayable());
//        return penaltyToResponse;
//    }
//}
//
