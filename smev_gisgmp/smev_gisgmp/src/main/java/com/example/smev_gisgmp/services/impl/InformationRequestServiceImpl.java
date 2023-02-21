package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import com.example.smev_gisgmp.services.InformationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.example.smev_gisgmp.constants.Constants.DELETE_INFORMATION;
import static com.example.smev_gisgmp.constants.Constants.INSERT_INFORMATION;
import static com.example.smev_gisgmp.constants.Constants.SELECT_INFORMATION_QUERY;
import static com.example.smev_gisgmp.constants.Constants.SELECT_INFORMATION_QUERY_FROM_QUEUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class InformationRequestServiceImpl implements InformationRequestService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public InformationRequest getInformationRequestByVehicleCertificate(String vehicleCertificate) {
        return jdbcTemplate.query(SELECT_INFORMATION_QUERY, new BeanPropertyRowMapper<>(InformationRequest.class), new Object[]{vehicleCertificate})
                .stream().findAny().orElse(null);
    }

    @Override
    public InformationRequest saveInformationRequest(InformationRequest informationRequest) {
        InformationRequest informationRequest1 = getInformationRequestByVehicleCertificate(informationRequest.getVehicleCertificate());
        if(informationRequest1 == null){
            informationRequest.setId(UUID.randomUUID());
            jdbcTemplate.update(INSERT_INFORMATION,informationRequest.getId(), informationRequest.getVehicleCertificate());
        }

        log.info(informationRequest + "from save method");
        return informationRequest;
    }

    @Override
    public Optional<InformationRequest> getInformationRequest() {
        return Optional.of(jdbcTemplate.query(SELECT_INFORMATION_QUERY_FROM_QUEUE, new BeanPropertyRowMapper<>(InformationRequest.class))
                .stream()
                .findAny()
                .orElse(new InformationRequest()));
    }

    @Override
    public void deleteInformationRequest(String vehicleCertificate) {
        jdbcTemplate.update(DELETE_INFORMATION, vehicleCertificate);
    }
}

