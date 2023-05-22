package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.Faculty;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Faculty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>, JpaSpecificationExecutor<Faculty> {
}
