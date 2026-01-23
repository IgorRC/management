package com.gniuscode.sie.management.rrhh.repository;

import com.gniuscode.sie.management.rrhh.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByDni(String dni);
    boolean existsByCorporateEmail(String corporateEmail);

    Optional<Employee> findByDni(String dni);
    Optional<Employee> findByUserId(Long userId);
}
