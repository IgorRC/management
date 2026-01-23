package com.gniuscode.sie.management.rrhh.repository;

import com.gniuscode.sie.management.rrhh.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByTitle(String title);

    Optional<Job> findByTitle(String title);
}
