package com.example.adapter.service;

import com.example.adapter.model.dto.PenaltyDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PenaltyClientService {

    ResponseEntity<List<PenaltyDto>> getPenalty(String vehicleCertificate);

}
