package com.example.smev_gisgmp.repository;

import com.example.smev_gisgmp.entity.AcknowledgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcknowledgeRepository extends JpaRepository<AcknowledgeEntity, Long> {
}
