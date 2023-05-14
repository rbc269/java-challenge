package jp.co.axa.apidemo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    public Employee() {

    }

    public Employee(String name, Double salary, String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @NotNull
    @Min(0)
    @Column(name = "EMPLOYEE_SALARY")
    private Double salary;

    @NotNull
    @Column(name = "DEPARTMENT")
    private String department;

}
