package com.example.adapter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Acknowledge {

    private UUID responseId;
}
