package com.example.adapter.service;

import com.example.adapter.model.Penalty;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PenaltyClientService {

    ResponseEntity<List<Penalty>> getPenalty(String vehicleCertificate);

}
