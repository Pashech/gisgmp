package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.smev_gisgmp.constants.Constants.DELETE_ALL_PENALTIES_TO_RESPONSE;
import static com.example.smev_gisgmp.constants.Constants.INSERT_PENALTY_TO_RESPONSE;
import static com.example.smev_gisgmp.constants.Constants.SELECT_PENALTY_TO_RESPONSE;

@Service
@RequiredArgsConstructor
public class PenaltyToResponseServiceImpl implements PenaltyToResponseService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void deleteAllPenalties() {
        jdbcTemplate.update(DELETE_ALL_PENALTIES_TO_RESPONSE);

    }
    @Override
    public int savePenaltyToResponse(PenaltyToResponse penaltyToResponse) {
        return jdbcTemplate.update(INSERT_PENALTY_TO_RESPONSE,
                penaltyToResponse.getId(),
                penaltyToResponse.getAccruedAmount(),
                penaltyToResponse.getAmountPayable(),
                penaltyToResponse.getResolutionNumber(),
                penaltyToResponse.getVehicleCertificate(),
                penaltyToResponse.getDecisionDate(),
                penaltyToResponse.getArticleKoAP(),
                penaltyToResponse.getResponseId()
        );
    }
    @Override
    public List<PenaltyToResponse> getAllPenalties() {
        return jdbcTemplate.query(SELECT_PENALTY_TO_RESPONSE, new BeanPropertyRowMapper<>(PenaltyToResponse.class));
    }
}
