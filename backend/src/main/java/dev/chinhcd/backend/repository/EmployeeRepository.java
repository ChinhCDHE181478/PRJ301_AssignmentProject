package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.dtos.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT new dev.chinhcd.backend.dtos.EmployeeDTO(e.employeeId, e.employeeName, d.departmentName) " +
            "FROM Employee e JOIN e.department d WHERE e.employeeId = :employeeId")
    Optional<EmployeeDTO> findEmployeeWithDepartmentById(@Param("employeeId") Integer employeeId);
}
