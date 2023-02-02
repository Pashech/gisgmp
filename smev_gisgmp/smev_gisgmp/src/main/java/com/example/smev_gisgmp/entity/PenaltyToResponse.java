package com.example.smev_gisgmp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PenaltyToResponse {

    private Long id;
    private BigDecimal accruedAmount;
    private BigDecimal amountPayable;
    private int resolutionNumber;
    private String vehicleCertificate;
    private LocalDate decisionDate;
    private String articleKoAP;
    private Long responseId;
}
