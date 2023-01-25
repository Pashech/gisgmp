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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity
@Table(name = "information_of_penalty")
@Getter
@Setter
@ToString
public class InformationRequest {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "vehicle_certificate")
    private String vehicleCertificate;
}
