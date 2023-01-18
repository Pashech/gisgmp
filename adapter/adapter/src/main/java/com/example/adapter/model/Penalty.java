package com.example.adapter.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Penalty {

    private BigDecimal accruedAmount;
    private BigDecimal amountPayable;
    private int resolutionNumber;
    private String vehicleCertificate;
    private LocalDate decisionDate;
    private String articleKoAP;
    private Long responseId;
}
