package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PenaltyToResponseServiceImpl implements PenaltyToResponseService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<PenaltyToResponse> getPenaltyToResponseById(Long id) {
        return null;
    }

    @Override
    public void deleteAllPenalties() {
        jdbcTemplate.update("DELETE FROM penaltyToResponse");

    }

    @Override
    public int savePenaltyToResponse(PenaltyToResponse penaltyToResponse) {
        return jdbcTemplate.update("INSERT INTO penaltyToResponse VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
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
        return jdbcTemplate.query("SELECT * FROM penaltyToResponse", new BeanPropertyRowMapper<>(PenaltyToResponse.class));
    }
}
