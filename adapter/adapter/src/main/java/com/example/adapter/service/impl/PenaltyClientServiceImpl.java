package com.example.adapter.service.impl;

import com.example.adapter.client.ClientHttp;
import com.example.adapter.exception_handling.NoPenaltyFoundException;
import com.example.adapter.model.Acknowledge;
import com.example.adapter.model.Penalty;
import com.example.adapter.service.PenaltyClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltyClientServiceImpl implements PenaltyClientService {

    private final ClientHttp clientHttp;

    @Override
    public ResponseEntity<List<Penalty>> getPenalty(String vehicleCertificate) {
        ResponseEntity<List<Penalty>> penaltyList = clientHttp.getPenaltyFromSmev(vehicleCertificate);
        if(penaltyList.getBody().size() == 0){
            throw new NoPenaltyFoundException("No any penalty");
        }
        if(penaltyList.getStatusCode().is2xxSuccessful() && penaltyList.getBody().size() != 0){
            Long responseId = penaltyList.getBody().get(0).getResponseId();
            Acknowledge acknowledge = new Acknowledge();
            acknowledge.setResponseId(responseId);
            clientHttp.sendAcknowledge(acknowledge);
        }
        for(Penalty p : penaltyList.getBody()){
            System.out.println(p);
        }
        return penaltyList;
    }
}
