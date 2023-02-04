package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.services.InformationRequestService;
import com.example.smev_gisgmp.services.PenaltyService;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltyServiceImpl implements PenaltyService {

    private final InformationRequestService informationRequestService;
    private final PenaltyToResponseService penaltyToResponseRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<PenaltyToResponse> getPenalty(String vehicleCertificate) {

        List<PenaltyToResponse> penaltyToResponseList1;

        Thread thread = new Thread(() -> {
            try {
                log.info("Worker has started!!!");
                InformationRequest informationRequest = informationRequestService.getInformationRequest(vehicleCertificate);
                log.info(informationRequest + "from impl");
                log.info("Request ID : " + informationRequest.getId());
                List<Penalty> penalties = getPenaltiesByVehicleCertificate(informationRequest.getVehicleCertificate());
                log.info(penalties + " from penalties");

                List<PenaltyToResponse> penaltyToResponseList = new ArrayList<>();
                for (Penalty penalty : penalties) {
                    penaltyToResponseList.add(changePenaltyToPenaltyResponse(penalty));
                }
                for (PenaltyToResponse p : penaltyToResponseList) {
                    p.setResponseId(informationRequest.getId());
                }
                log.info(penaltyToResponseList + "penalty to response list");
                for(PenaltyToResponse penaltyToResponse : penaltyToResponseList){
                    penaltyToResponseRepository.savePenaltyToResponse(penaltyToResponse);
                    log.info("save");
                }
                //List<PenaltyToResponse> all = penaltyToResponseRepository.getAllPenalties();
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("server out of order");
            }
        });

        thread.start();

        penaltyToResponseList1 = penaltyToResponseRepository.getAllPenalties();
        log.info(penaltyToResponseList1 + "penalty to response");
//        int simulatorChance = (int) (Math.random() * 10);
//        log.info(String.valueOf(simulatorChance));
//        if (simulatorChance == 1 || simulatorChance == 2 || simulatorChance == 3 || simulatorChance == 4) {
//            penaltyToResponseList1 = penaltyToResponseRepository.getAllPenalties();
//        }
        return penaltyToResponseList1;
    }

    @Override
    public List<Penalty> getPenaltiesByVehicleCertificate(String vehicleCertificate) {
        return jdbcTemplate.query("SELECT * FROM penalties WHERE vehicleCertificate = ?", new BeanPropertyRowMapper<>(Penalty.class), vehicleCertificate);
    }

        public static PenaltyToResponse changePenaltyToPenaltyResponse(Penalty penalty) {
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
