package com.example.smev_gisgmp.controller;

import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import com.example.smev_gisgmp.services.PenaltyService;
import com.example.smev_gisgmp.services.PenaltyToResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyService penaltyService;
    private final PenaltyToResponseService penaltyToResponseService;

    @GetMapping("/get/penalties/{vehicleCertificate}")
    public ResponseEntity<List<PenaltyToResponse>> getPenalties(@PathVariable String vehicleCertificate) throws InterruptedException {
        return new ResponseEntity<>(penaltyService.getPenalty(vehicleCertificate), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void delete(){
        penaltyToResponseService.deleteAllPenalties();
    }
}
