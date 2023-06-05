package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.Faculty;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for the Faculty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByExternalId(Long externalId);
}
