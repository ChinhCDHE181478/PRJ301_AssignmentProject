package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByDepartmentName(String name);
}
