package jp.co.axa.apidemo.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.util.JsonUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testDeleteEmployeeSuccess() throws Exception {
        Long id = 11L;

        // Existing employee data
        Employee existingEmployee = new Employee("Ringo", 88888.88, "Liverpool Dept");

        given(service.getEmployee(id)).willReturn(existingEmployee);
        doNothing().when(service).deleteEmployee(id);

        mvc.perform(delete("/employees/{id}", id)).andExpect(status().isOk());

        verify(service, VerificationModeFactory.times(1)).deleteEmployee(Mockito.any());

        reset(service);
    }

    @Test
    public void testUpdateEmployeeSuccess() throws Exception {
        Long id = 22L;

        // Existing employee data
        Employee existingEmployee = new Employee("John", 55555555.55, "DeptAA");

        // Updated employee data
        Employee updatedEmployee = new Employee("Paul", 7777777.77, "DeptBB");

        given(service.getEmployee(id)).willReturn(existingEmployee);
        given(service.saveEmployee(Mockito.any())).willReturn(updatedEmployee);

        mvc.perform(put("/employees/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toString(updatedEmployee))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedEmployee.getName()))
                .andExpect(jsonPath("$.salary").value(updatedEmployee.getSalary()))
                .andExpect(jsonPath("$.department").value(updatedEmployee.getDepartment()));

        verify(service, VerificationModeFactory.times(1)).getEmployee(Mockito.any());
        verify(service, VerificationModeFactory.times(1)).saveEmployee(Mockito.any());

        reset(service);
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        Long id = 3333333333333L;
        Employee updatedEmployee = new Employee("Mason", 88888.88, "DeptBB");

        mvc.perform(put("/employees/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toString(updatedEmployee))).andExpect(status().isNotFound());

        verify(service, VerificationModeFactory.times(1)).getEmployee(Mockito.any());

        reset(service);
    }

    @Test
    public void testGetEmployeeSuccess() throws Exception {
        Long id = 44L;
        // Existing employee data
        Employee existingEmployee = new Employee("Peter", 222.00, "Faculty");

        given(service.getEmployee(id)).willReturn(existingEmployee);
        mvc.perform(get("/employees/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(existingEmployee.getName())))
                .andExpect(jsonPath("$.salary", is(existingEmployee.getSalary())))
                .andExpect(jsonPath("$.department", is(existingEmployee.getDepartment())));

        verify(service, VerificationModeFactory.times(1)).getEmployee(Mockito.any());

        reset(service);
    }

    @Test
    public void testGetEmployeeNotFound() throws Exception {
        mvc.perform(get("/employees/444")).andExpect(status().isNotFound());

        verify(service, VerificationModeFactory.times(1)).getEmployee(Mockito.any());
        reset(service);
    }

    @Test
    public void testSaveEmployeeSuccess() throws Exception {
        Employee employee = new Employee("Mary", 5555.50, "Tech Dept");

        given(service.saveEmployee(Mockito.any())).willReturn(employee);

        mvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(employee)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$.salary", is(employee.getSalary())))
                .andExpect(jsonPath("$.department", is(employee.getDepartment())));

        verify(service, VerificationModeFactory.times(1)).saveEmployee(Mockito.any());
        reset(service);
    }

    @Test
    public void testRetrieveEmployeesSuccess() throws Exception {
        Employee emp1 = new Employee("Peter", 22.00, "dept1");
        Employee emp2 = new Employee("Paul", 55.55, "dept2");
        Employee emp3 = new Employee("Mary", 99.99, "dept3");

        given(service.retrieveEmployees()).willReturn(Arrays.asList(emp1, emp2, emp3));

        mvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(emp1.getName())))
                .andExpect(jsonPath("$[1].name", is(emp2.getName())))
                .andExpect(jsonPath("$[2].name", is(emp3.getName())))
                .andExpect(jsonPath("$[0].salary", is(emp1.getSalary())))
                .andExpect(jsonPath("$[1].salary", is(emp2.getSalary())))
                .andExpect(jsonPath("$[2].salary", is(emp3.getSalary())))
                .andExpect(jsonPath("$[0].department", is(emp1.getDepartment())))
                .andExpect(jsonPath("$[1].department", is(emp2.getDepartment())))
                .andExpect(jsonPath("$[2].department", is(emp3.getDepartment())));

        verify(service, VerificationModeFactory.times(1)).retrieveEmployees();
        reset(service);
    }

    @Test
    public void testRetrieveEmployeesEmpty() throws Exception {
        mvc.perform(get("/employees")).andExpect(status().isNoContent());
        verify(service, VerificationModeFactory.times(1)).retrieveEmployees();
    }
}
