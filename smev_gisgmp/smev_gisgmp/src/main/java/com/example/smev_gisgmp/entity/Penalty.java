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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "penalties")
@Getter
@Setter
@ToString
public class Penalty {

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
}
