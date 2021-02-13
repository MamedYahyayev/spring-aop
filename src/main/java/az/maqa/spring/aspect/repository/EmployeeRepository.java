package az.maqa.spring.aspect.repository;

import az.maqa.spring.aspect.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Spring Data Jpa repository for Employee Entity
 */
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
