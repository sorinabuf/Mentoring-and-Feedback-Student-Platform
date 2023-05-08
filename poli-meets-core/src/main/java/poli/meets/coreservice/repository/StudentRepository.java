package poli.meets.coreservice.repository;

import java.util.List;
import java.util.Optional;

import poli.meets.coreservice.domain.Student;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByStudentEmail(String username);
}
