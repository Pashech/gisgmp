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

@Entity
@Table(name = "penalty_to_response")
@Getter
@Setter
@ToString
public class PenaltyToResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "accrued_amount")
    private BigDecimal accruedAmount;
    @Column(name = "amount_payable")
    private BigDecimal amountPayable;
    @Column(name = "resolution_number")
    private int resolutionNumber;
    @Column(name = "vehicle_certificate")
    private String vehicleCertificate;
    @Column(name = "decision_date")
    private LocalDate decisionDate;
    @Column(name = "article_koap")
    private String articleKoAP;
    @Column(name = "response_id")
    private Long responseId;
}
