package com.example.adapter.mapper;

import com.example.adapter.model.Penalty;
import com.example.adapter.model.dto.PenaltyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PenaltyMapper {

    @Mapping(target = "accruedAmount", source = "penalty.accruedAmount")
    @Mapping(target = "amountPayable", source = "penalty.amountPayable")
    @Mapping(target = "resolutionNumber", source = "penalty.resolutionNumber")
    @Mapping(target = "vehicleCertificate", source = "penalty.vehicleCertificate")
    @Mapping(target = "decisionDate", source = "penalty.decisionDate")
    @Mapping(target = "articleKoAP", source = "penalty.articleKoAP")
    PenaltyDto penaltyToDto(Penalty penalty);
}
