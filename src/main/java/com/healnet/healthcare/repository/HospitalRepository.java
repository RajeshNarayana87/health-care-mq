package com.healnet.healthcare.repository;

import com.healnet.healthcare.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query("SELECT h FROM Hospital h WHERE h.isDeleted = false")
    Optional<List<Hospital>> findByIsNotDeleted();

    Optional<Hospital> findByIdAndIsDeleted(Long hospitalId, Boolean isDeleted);
}
