package com.healnet.healthcare.repository;

import com.healnet.healthcare.entity.Clinic;
import com.healnet.healthcare.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    @Query("SELECT t FROM Treatment t WHERE t.isDeleted = false")
    Optional<List<Treatment>> findByIsNotDeleted();
}
