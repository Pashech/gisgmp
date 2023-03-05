package com.example.adapter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement(name = "penalty")
public class Penalty {

    private BigDecimal accruedAmount;
    private BigDecimal amountPayable;
    private int resolutionNumber;
    private String vehicleCertificate;
    private LocalDate decisionDate;
    private String articleKoAP;
    private UUID responseId;
}
