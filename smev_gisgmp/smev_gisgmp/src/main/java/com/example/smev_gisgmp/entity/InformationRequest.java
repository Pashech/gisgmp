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

@Getter
@Setter
@ToString
public class InformationRequest {

    private UUID id;
    @Pattern(regexp = "[A-Z]{3}[0-9]{6}", message = "the certificate does not match the format, for example 'XXX111111'")
    private String vehicleCertificate;
}
