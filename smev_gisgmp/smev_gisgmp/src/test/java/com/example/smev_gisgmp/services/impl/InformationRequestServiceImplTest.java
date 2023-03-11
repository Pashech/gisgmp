package com.example.smev_gisgmp.services.impl;

import com.example.smev_gisgmp.entity.InformationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;


import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ExtendWith(MockitoExtension.class)
class InformationRequestServiceImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private InformationRequest informationRequest;
    private InformationRequestServiceImpl testSubject;

    @BeforeEach
    public void setUp(){

        testSubject = new InformationRequestServiceImpl(jdbcTemplate);
        informationRequest = new InformationRequest();
        informationRequest.setVehicleCertificate("ETA123456");
    }

    @Test
    void getInformationRequestByVehicleCertificate() {
        testSubject.saveInformationRequest(informationRequest);
        InformationRequest infRequest = testSubject.getInformationRequestByVehicleCertificate("ETA123456");
        assertEquals(informationRequest.getVehicleCertificate(), infRequest.getVehicleCertificate());
    }

    @Test
    void deleteInformationRequest() {
        testSubject.saveInformationRequest(informationRequest);testSubject.deleteInformationRequest("ETA123456");
        InformationRequest deletedRequest = testSubject.getInformationRequestByVehicleCertificate("ETA123456");
        assertNull(deletedRequest);
    }
}