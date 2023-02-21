package com.example.smev_gisgmp.worker;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.exception_handling.NoPenaltyException;
import com.example.smev_gisgmp.exception_handling.thread.HandlerThreadFactory;
import com.example.smev_gisgmp.services.InformationRequestService;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.smev_gisgmp.constants.Constants.SELECT_PENALTY_QUERY;
import static com.example.smev_gisgmp.util.Util.changePenaltyToPenaltyResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class Worker {

    private final InformationRequestService informationRequestService;
    private final PenaltyToResponseService penaltyToResponseRepository;
    private final JdbcTemplate jdbcTemplate;
    private ExecutorService executorService;

    @PostConstruct
    public void run() {
        executorService = Executors.newSingleThreadExecutor(new HandlerThreadFactory());
        executorService.execute(() -> {
            while (true) {
                log.info("Worker has started!!!");
                Optional<InformationRequest> informationRequest = informationRequestService.getInformationRequest();
                while (informationRequest.get().getVehicleCertificate() == null) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    informationRequest = informationRequestService.getInformationRequest();
                    log.info(informationRequest.get().getVehicleCertificate() + "from worker repeating");
                }

                List<Penalty> penalties = getPenaltiesByVehicleCertificate(informationRequest.get().getVehicleCertificate());
                if (penalties.size() == 0) {
                    throw new NoPenaltyException("Penalty not found");
                }

                List<PenaltyToResponse> penaltyToResponseList = new ArrayList<>();
                for (Penalty penalty : penalties) {
                    penaltyToResponseList.add(changePenaltyToPenaltyResponse(penalty));
                }

                for (PenaltyToResponse p : penaltyToResponseList) {
                    p.setResponseId(informationRequest.get().getId());
                }

                List<PenaltyToResponse> all = penaltyToResponseRepository.getAllPenalties();
                if (all.size() == 0) {
                    for (PenaltyToResponse penaltyToResponse : penaltyToResponseList) {
                        penaltyToResponseRepository.savePenaltyToResponse(penaltyToResponse);
                        informationRequestService.deleteInformationRequest(informationRequest.get().getVehicleCertificate());
                    }
                }
                log.info("worker finished");
            }
        });
    }

    public List<Penalty> getPenaltiesByVehicleCertificate(String vehicleCertificate) {
        return jdbcTemplate.query(SELECT_PENALTY_QUERY, new BeanPropertyRowMapper<>(Penalty.class), vehicleCertificate);
    }

    @PreDestroy
    public void shutDown(){
        executorService.shutdown();
        log.info("Worker was stopped");
    }
}
