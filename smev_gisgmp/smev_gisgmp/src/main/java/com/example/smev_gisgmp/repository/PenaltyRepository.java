package com.example.smev_gisgmp.repository;

import com.example.smev_gisgmp.entity.Penalty;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.util.List;
import java.util.Optional;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    List<Penalty> getPenaltiesByVehicleCertificate(String vehicleCertificate);

}
