package com.example.adapter.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
@Getter
@Setter
public class InfoRequest {
    @Pattern(regexp = "[A-Z]{3}[1-9]{6}", message = "only letters")
    private String vehicleCertificate;
}
