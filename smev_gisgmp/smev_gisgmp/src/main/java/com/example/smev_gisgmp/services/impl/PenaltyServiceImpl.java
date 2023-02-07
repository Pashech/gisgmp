package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.exception_handling.InformationRequestException;
import com.example.smev_gisgmp.exception_handling.NoPenaltyException;
import com.example.smev_gisgmp.exception_handling.ServerSvemException;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.smev_gisgmp.constants.Constants.SELECT_PENALTY_QUERY;

@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltyServiceImpl implements PenaltyService {

    private final InformationRequestService informationRequestService;
    private final PenaltyToResponseService penaltyToResponseRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<PenaltyToResponse> getPenalty(String vehicleCertificate) throws InterruptedException, NoPenaltyException, ServerSvemException  {
        List<PenaltyToResponse> penaltyToResponseList1;

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            log.info("Worker has started!!!");
            InformationRequest informationRequest = informationRequestService.getInformationRequest(vehicleCertificate).orElseThrow(
                    () -> new InformationRequestException("Information request not found"));
            List<Penalty> penalties = getPenaltiesByVehicleCertificate(informationRequest.getVehicleCertificate());
            if (penalties.size() == 0) {
                throw new NoPenaltyException("Penalty not found");
            }

            int x = 0;
            int count = 0;
            while (x <= 0) {
                x = (int) (Math.random() * 10 - 5);
                log.info(String.valueOf(x));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                if (count == 4) {
                    log.error("Server is unavailable");
                    throw new ServerSvemException("Server is unavailable");
                }
            }

            List<PenaltyToResponse> penaltyToResponseList = new ArrayList<>();
            for (Penalty penalty : penalties) {
                penaltyToResponseList.add(changePenaltyToPenaltyResponse(penalty));
            }
            for (PenaltyToResponse p : penaltyToResponseList) {
                p.setResponseId(informationRequest.getId());
            }
            List<PenaltyToResponse> all = penaltyToResponseRepository.getAllPenalties();
            if (all.size() == 0) {
                for (PenaltyToResponse penaltyToResponse : penaltyToResponseList) {
                    penaltyToResponseRepository.savePenaltyToResponse(penaltyToResponse);
                    informationRequestService.deleteInformationRequest(vehicleCertificate);
                }
            }
            log.info("worker finished");
        });
        executorService.shutdown();

        Thread.sleep(1000);

        penaltyToResponseList1 = penaltyToResponseRepository.getAllPenalties();
        return penaltyToResponseList1;
    }

    @Override
    public List<Penalty> getPenaltiesByVehicleCertificate(String vehicleCertificate) {
        return jdbcTemplate.query(SELECT_PENALTY_QUERY, new BeanPropertyRowMapper<>(Penalty.class), vehicleCertificate);
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
