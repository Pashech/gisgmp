package com.example.adapter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoRequest {
    @Pattern(regexp = "[A-Z]{3}[1-9]{6}", message = "only letters")
    private String vehicleCertificate;
}
