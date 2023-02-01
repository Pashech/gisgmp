package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.services.InformationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InformationRequestServiceImpl implements InformationRequestService {

    private final JdbcTemplate jdbcTemplate;
    private final static String SELECT_QUERY = "SELECT id FROM information WHERE vehicleCertificate=?";

    @Override
    public InformationRequest saveInformationRequest(InformationRequest informationRequest) {
        jdbcTemplate.update("INSERT INTO information VALUES(1, ?)", informationRequest.getVehicleCertificate());
        InformationRequest informationRequest1 = jdbcTemplate.query(SELECT_QUERY, new BeanPropertyRowMapper<>(InformationRequest.class), new Object[]{informationRequest.getVehicleCertificate()})
                .stream()
                .findAny().orElse(new InformationRequest());
        log.info(String.valueOf(informationRequest1));


        return informationRequest;
    }
}
