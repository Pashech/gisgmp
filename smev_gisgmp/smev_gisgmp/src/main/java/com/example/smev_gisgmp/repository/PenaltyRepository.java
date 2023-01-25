package com.example.smev_gisgmp.repository;

import com.example.smev_gisgmp.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    List<Penalty> getPenaltiesByVehicleCertificate(String vehicleCertificate);

}
