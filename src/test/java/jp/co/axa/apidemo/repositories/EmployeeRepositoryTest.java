package jp.co.axa.apidemo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.axa.apidemo.ApiDemoApplication;
import jp.co.axa.apidemo.entities.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiDemoApplication.class)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = repository.save(new Employee("Caren", 999.99, "Idaho Grp"));

        assertThat(employee.getId()).isNotNull();
        assertThat(employee.getName()).isEqualTo("Caren");
        assertThat(employee.getSalary()).isEqualTo(999.99);
        assertThat(employee.getDepartment()).isEqualTo("Idaho Grp");
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = repository.save(new Employee("Kurt Cobain", 666.66, "Seattle Div"));

        Optional<Employee> foundEmployee = repository.findById(employee.getId());

        assertThat(foundEmployee.isPresent()).isTrue();
        assertThat(foundEmployee.get().getName()).isEqualTo(employee.getName());
    }

    @Test
    public void testFindAllEmployees() {
        Employee employee1 = repository.save(new Employee("Michael Jordan", 2323.23, "Chicago Bulls"));
        Employee employee2 = repository.save(new Employee("Scottie Pippen", 3333.33, "Chicago Bears"));
        Employee employee3 = repository.save(new Employee("Dennis Rodman", 9696.96, "Chicago Cubs"));

        List<Employee> employees = repository.findAll();

        assertThat(employees).hasSize(3);
        assertThat(employees.get(0).getName()).isEqualTo(employee1.getName());
        assertThat(employees.get(0).getSalary()).isEqualTo(employee1.getSalary());
        assertThat(employees.get(0).getDepartment()).isEqualTo(employee1.getDepartment());

        assertThat(employees.get(1).getName()).isEqualTo(employee2.getName());
        assertThat(employees.get(1).getSalary()).isEqualTo(employee2.getSalary());
        assertThat(employees.get(1).getDepartment()).isEqualTo(employee2.getDepartment());

        assertThat(employees.get(2).getName()).isEqualTo(employee3.getName());
        assertThat(employees.get(2).getSalary()).isEqualTo(employee3.getSalary());
        assertThat(employees.get(2).getDepartment()).isEqualTo(employee3.getDepartment());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = repository.save(new Employee("Caren", 999.99, "Idaho Grp"));
        employee.setName("Carpenter");
        employee.setSalary(888.88);
        employee.setDepartment("Memphis Div");

        Employee updatedEmployee = repository.save(employee);

        assertThat(updatedEmployee.getName()).isEqualTo(employee.getName());
        assertThat(updatedEmployee.getSalary()).isEqualTo(employee.getSalary());
        assertThat(updatedEmployee.getDepartment()).isEqualTo(employee.getDepartment());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = repository.save(new Employee("John Wall", 1231.23, "Washington Wizards"));

        repository.deleteById(employee.getId());

        Optional<Employee> deletedUser = repository.findById(employee.getId());
        assertThat(deletedUser.isPresent()).isFalse();
    }

}
