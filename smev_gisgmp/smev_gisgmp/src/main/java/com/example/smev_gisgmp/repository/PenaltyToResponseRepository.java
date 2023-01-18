package com.example.smev_gisgmp.repository;

import com.example.smev_gisgmp.entity.PenaltyToResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyToResponseRepository extends JpaRepository<PenaltyToResponse, Long> {
}
