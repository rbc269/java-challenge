package jp.co.axa.apidemo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Cacheable("employees")
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @Cacheable("employee")
    public Employee getEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        return employee;
    }

    @Caching(evict = { @CacheEvict(value = "employee", allEntries = true),
            @CacheEvict(value = "employees", allEntries = true) })
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Cacheable("employees")
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Caching(evict = { @CacheEvict(value = "employee", allEntries = true),
            @CacheEvict(value = "employees", allEntries = true) })
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
