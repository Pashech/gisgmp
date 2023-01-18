package com.example.smev_gisgmp.repository;

import com.example.smev_gisgmp.entity.InformationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformationRequestRepository extends JpaRepository<InformationRequest, Long> {
    InformationRequest getInformationRequestByVehicleCertificate(String vehicleCertificate);
}

