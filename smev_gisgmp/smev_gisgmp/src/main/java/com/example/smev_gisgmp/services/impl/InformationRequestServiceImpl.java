package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.services.InformationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InformationRequestServiceImpl implements InformationRequestService {

    private final JdbcTemplate jdbcTemplate;
    private final static String SELECT_QUERY = "SELECT * FROM information WHERE vehicleCertificate=?";

    @Override
    public InformationRequest saveInformationRequest(InformationRequest informationRequest) {
        informationRequest.setId(UUID.randomUUID());
        jdbcTemplate.update("INSERT INTO information VALUES(?, ?)",informationRequest.getId(), informationRequest.getVehicleCertificate());
        log.info(informationRequest + "from save method");
        return informationRequest;
    }

    @Override
    public InformationRequest getInformationRequest(String vehicleCertificate) {
        return jdbcTemplate.query(SELECT_QUERY, new BeanPropertyRowMapper<>(InformationRequest.class), new Object[]{vehicleCertificate})
                .stream()
                .findAny()
                .orElse(new InformationRequest());
    }

    @Override
    public void deleteInformationRequest(String vehicleCertificate) {
        jdbcTemplate.update("DELETE FROM information WHERE vehicleCertificate = ?", vehicleCertificate);
    }
}

