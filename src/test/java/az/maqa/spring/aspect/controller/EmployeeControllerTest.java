package az.maqa.spring.aspect.controller;

import az.maqa.spring.aspect.domain.Employee;
import az.maqa.spring.aspect.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  Test class for Employee Controller
 *  @see EmployeeController
 */
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    /**
     * Test method for createEmployee
     *
     * @throws Exception when content is null
     */
    @Test
    void createEmployeeTest() throws Exception {
        Employee employee = new Employee();
        employee.setName("Samir");
        employee.setSurname("Samirov");
        employee.setSalary(null);

        ObjectMapper mapper = new ObjectMapper();
        byte[] employeeBytes = mapper.writeValueAsBytes(employee);

        mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(employeeBytes))
                .andExpect(status().is2xxSuccessful());

        then(employeeService).should().createEmployee(any());
    }
}