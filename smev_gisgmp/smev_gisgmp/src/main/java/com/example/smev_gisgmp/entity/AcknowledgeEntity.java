package com.example.smev_gisgmp.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AcknowledgeEntity {

    private UUID id;
    private UUID responseId;
}
