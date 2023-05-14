package jp.co.axa.apidemo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * retrieves all employees data from the db
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * retrieves the employee data, using employee's id as parameter
     *
     * @param id employee id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        try {
            Optional<Employee> employee = Optional.ofNullable(employeeService.getEmployee(id));
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * saves the new employee data
     *
     * @param employee in jason format
     */
    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        try {
            Employee employee_ = employeeService.saveEmployee(employee);
            return new ResponseEntity<>(employee_, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * deletes the employee data, using employee's id as parameter
     *
     * @param id employee id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * deletes the employee data, using employee's id as parameter
     *
     * @param employee in jason format
     * @param id       employee id
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        try {
            Optional<Employee> employee_ = Optional.ofNullable(employeeService.getEmployee(id));
            if (employee_.isPresent()) {
                employee.setId(id);
                return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
