package com.example.adapter.service.impl;

import com.example.adapter.client.ClientHttp;
import com.example.adapter.exception_handling.NoPenaltyFoundException;
import com.example.adapter.mapper.PenaltyMapper;
import com.example.adapter.model.Acknowledge;
import com.example.adapter.model.Penalty;
import com.example.adapter.model.dto.PenaltyDto;
import com.example.adapter.service.PenaltyClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PenaltyClientServiceImpl implements PenaltyClientService {

    private final ClientHttp clientHttp;
    private final PenaltyMapper penaltyMapper;

    @Override
    public ResponseEntity<List<PenaltyDto>> getPenalty(String vehicleCertificate) {
        ResponseEntity<List<Penalty>> penaltyList = clientHttp.getPenaltyFromSmev(vehicleCertificate);
        List<PenaltyDto> penaltyDtoList = new ArrayList<>();
        for(int i = 0; i < Objects.requireNonNull(penaltyList.getBody()).size(); i++){
            penaltyDtoList.add(penaltyMapper.penaltyToDto(penaltyList.getBody().get(i)));
        }
        if(penaltyList.getBody().size() == 0){
            throw new NoPenaltyFoundException("No any penalty");
        }
//        if(penaltyList.getStatusCode().is2xxSuccessful() && penaltyList.getBody().size() != 0){
//            UUID responseId = penaltyList.getBody().get(0).getResponseId();
//            Acknowledge acknowledge = new Acknowledge();
//            acknowledge.setResponseId(responseId);
//            clientHttp.sendAcknowledge(acknowledge);
//        }
        return new ResponseEntity<>(penaltyDtoList, HttpStatus.OK);
    }
}
