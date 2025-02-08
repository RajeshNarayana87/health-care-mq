package com.healnet.healthcare.repository;

import com.healnet.healthcare.entity.Clinic;
import com.healnet.healthcare.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    @Query("SELECT c FROM Clinic c WHERE c.isDeleted = false")
    Optional<List<Clinic>> findByIsNotDeleted();

    Optional<Clinic> findByIdAndIsDeleted(Long clinicId, boolean isDeleted);
}
