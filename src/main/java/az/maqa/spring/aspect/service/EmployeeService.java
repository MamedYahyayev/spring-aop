package az.maqa.spring.aspect.service;

import az.maqa.spring.aspect.domain.Employee;
import az.maqa.spring.aspect.model.dto.EmployeeDto;
import az.maqa.spring.aspect.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service layer for Employee object
 */
@Service
public class EmployeeService {

    public static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setSalary(employeeDto.getSalary());

        Employee savedEmployee = employeeRepository.save(employee);
        log.debug("Saved Employee {} ", savedEmployee);
        return savedEmployee;
    }
}
