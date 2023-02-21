package com.example.smev_gisgmp.util;

import com.example.smev_gisgmp.entity.Penalty;
import com.example.smev_gisgmp.entity.PenaltyToResponse;
import org.springframework.stereotype.Component;

@Component
public class Util {

    public static PenaltyToResponse changePenaltyToPenaltyResponse(Penalty penalty) {
        PenaltyToResponse penaltyToResponse = new PenaltyToResponse();
        penaltyToResponse.setAccruedAmount(penalty.getAccruedAmount());
        penaltyToResponse.setVehicleCertificate(penalty.getVehicleCertificate());
        penaltyToResponse.setResolutionNumber(penalty.getResolutionNumber());
        penaltyToResponse.setDecisionDate(penalty.getDecisionDate());
        penaltyToResponse.setArticleKoAP(penalty.getArticleKoAP());
        penaltyToResponse.setAmountPayable(penalty.getAmountPayable());
        return penaltyToResponse;
    }
}
