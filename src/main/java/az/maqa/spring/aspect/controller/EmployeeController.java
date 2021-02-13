package az.maqa.spring.aspect.controller;

import az.maqa.spring.aspect.domain.Employee;
import az.maqa.spring.aspect.model.dto.EmployeeDto;
import az.maqa.spring.aspect.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 *  Controller layer for Employee entity
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * POST  /api/employee
     *
     * @param employeeDto for creating employee
     * @return employee object with 200 status
     * @throws Exception when employeeDto object is null
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeDto) throws Exception {
        log.debug("EmployeeDto object for creating Employee {} ", employeeDto);
        if (employeeDto != null) {
            Employee employee = employeeService.createEmployee(employeeDto);
            return ResponseEntity.ok(employee);
        } else {
            throw new Exception("EmployeeDto object is null");
        }
    }


    /**
     * GET  /api/employee/{id} : get employee by id
     *
     * @param id - id value for Employee
     * @return Employee object with ResponseEntity 200 status
     * @throws Exception when id is empty
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) throws Exception {
        if (id != null) {
            Optional<Employee> employee = employeeService.findEmployeeById(id);
            return employee.map(e -> {
                EmployeeDto employeeDto = new EmployeeDto(e);
                return ResponseEntity.ok(employeeDto);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            throw new Exception("Id field can't be empty!!!");
        }
    }


}
