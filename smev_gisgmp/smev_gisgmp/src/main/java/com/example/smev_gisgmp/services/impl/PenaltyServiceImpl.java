package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.exception_handling.NoPenaltyException;
import com.example.smev_gisgmp.exception_handling.ServerSvemException;
import com.example.smev_gisgmp.services.PenaltyService;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.smev_gisgmp.constants.Constants.SELECT_PENALTY_QUERY;

@Service
@RequiredArgsConstructor
@Slf4j
public class PenaltyServiceImpl implements PenaltyService {
    private final PenaltyToResponseService penaltyToResponseRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<PenaltyToResponse> getPenalty(String vehicleCertificate) throws NoPenaltyException, ServerSvemException {
        return penaltyToResponseRepository.getAllPenalties();
    }

    @Override
    public List<Penalty> getPenaltiesByVehicleCertificate(String vehicleCertificate) {
        return jdbcTemplate.query(SELECT_PENALTY_QUERY, new BeanPropertyRowMapper<>(Penalty.class), vehicleCertificate);
    }
}
