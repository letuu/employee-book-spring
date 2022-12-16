package com.skypro.employeebookspring;

import com.skypro.employeebookspring.exception.InvalidEmployeeRequestExp;
import com.skypro.employeebookspring.exception.RepeatEmployeeException;
import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.record.EmployeeRequest;
import com.skypro.employeebookspring.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    public EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("NameOne", "LastNameOne", 1, 10000),
                new EmployeeRequest("NameTwo", "LastNameTwo", 2, 20000),
                new EmployeeRequest("NameThree", "LastNameThree", 3, 30000),
                new EmployeeRequest("NameFour", "LastNameFour", 4, 40000),
                new EmployeeRequest("NameFive", "LastNameFive", 5, 50000)
        ).forEach(e -> employeeService.addEmployee(e));
    }

    @Test
    public void addEmployee() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5); //это можно не проверять

        EmployeeRequest request = new EmployeeRequest("Valid", "Valid", 3, 10000);
        Employee result = employeeService.addEmployee(request);
//        Employee result2 = new Employee("ValidTwo", "ValidTwo", 1, 10); //для проверки
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getDepartment(), result.getDepartment());
        assertEquals(request.getSalary(), result.getSalary());

        Assertions.assertThat(employeeService.getAllEmployees()).contains(result);
//        Assertions.assertThat(employeeService.getAllEmployees()).contains(result2);

        Assertions.assertThat(employees).hasSize(6); //это можно не проверять
    }

    @Test
    public void addEmployeeInvalidName() {
        EmployeeRequest nullFirstName = new EmployeeRequest(null, "Valid", 3, 1);
        EmployeeRequest nullLastName = new EmployeeRequest("Valid", null, 3, 1);
        EmployeeRequest numberFirstName = new EmployeeRequest("Inv1", "Valid", 3, 1);
        EmployeeRequest numberFLastName = new EmployeeRequest("Valid", "Inv1", 3, 1);
        EmployeeRequest spaceVFirstName = new EmployeeRequest("In v", "Valid", 3, 1);
        EmployeeRequest spaceVFLastName = new EmployeeRequest("Valid", "In v", 3, 1);
        EmployeeRequest emptyFirstName = new EmployeeRequest("", "Valid", 3, 1);
        EmployeeRequest emptyFLastName = new EmployeeRequest("Valid", "", 3, 1);
        EmployeeRequest spaceFirstName = new EmployeeRequest(" ", "Valid", 3, 1);
        EmployeeRequest spaceFLastName = new EmployeeRequest("Valid", " ", 3, 1);
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(nullFirstName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(nullLastName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(numberFirstName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(numberFLastName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(spaceVFirstName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(spaceVFLastName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(emptyFirstName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(emptyFLastName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(spaceFirstName));
        assertThrows(InvalidEmployeeRequestExp.class, () -> employeeService.addEmployee(spaceFLastName));
    }

    @Test
    public void addEmployeeCapitalizeName() {
        EmployeeRequest request1 = new EmployeeRequest("ivan", "Ivanov", 1, 10);
        EmployeeRequest request2 = new EmployeeRequest("Anton", "antonov", 1, 10);
        Employee expected1 = new Employee("Ivan", "Ivanov", 1, 10);
        Employee expected2 = new Employee("Anton", "Antonov", 1, 10);
        Employee actual1 = employeeService.addEmployee(request1);
        Employee actual2 = employeeService.addEmployee(request2);
        assertEquals(expected1, actual1);
        assertEquals(expected2.getLastName(), actual2.getLastName());    //или так
    }

    @Test
    public void addEmployeeRepeat() {
        EmployeeRequest repeatAllField = new EmployeeRequest("NameOne", "LastNameOne", 1, 10000);
        EmployeeRequest repeatName = new EmployeeRequest("NameOne", "LastNameOne", 2, 20000);
        assertThrows(RepeatEmployeeException.class, () -> employeeService.addEmployee(repeatAllField));
        assertThrows(RepeatEmployeeException.class, () -> employeeService.addEmployee(repeatName));
    }

    @Test
    public void listEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5);
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getFirstName)
                .isEqualTo("NameOne");
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getLastName)
                .isEqualTo("LastNameOne");
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getDepartment)
                .isEqualTo(1);
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getSalary)
                .isEqualTo(10000);

        Assertions.assertThat(employees)
                .element(1)
                .extracting(Employee::getFirstName)
                .isEqualTo("NameTwo");
        Assertions.assertThat(employees)
                .element(2)
                .extracting(Employee::getFirstName)
                .isEqualTo("NameThree");
        Assertions.assertThat(employees)
                .element(3)
                .extracting(Employee::getFirstName)
                .isEqualTo("NameFour");
        Assertions.assertThat(employees)
                .element(4)
                .extracting(Employee::getFirstName)
                .isEqualTo("NameFive");
    }

    @Test
    public void sumOfSalaries() {
        int sum = employeeService.getSalarySum();
        Assertions.assertThat(sum).isEqualTo(150000);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = employeeService.getEmployeeMaxSalary();
        Assertions.assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("NameFive");
    }

    @Test
    public void employeeWithMinSalary() {
        Employee employee = employeeService.getEmployeeMinSalary();
        Assertions.assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("NameOne");
    }

    @Test
    public void EmployeeAboveAverageSalary() {
        List<Employee> aboveAverageSalaryList = employeeService.getEmployeeAboveAverageSalary();
        for (Employee employee : aboveAverageSalaryList) {
            assertTrue(employee.getSalary() > 30000);
        }
        Assertions.assertThat(aboveAverageSalaryList).hasSize(2)
                .contains(new Employee("NameFour", "LastNameFour", 4, 40000),
                        new Employee("NameFive", "LastNameFive", 5, 50000));
    }

    @Test
    public void removeEmployee() {
        employeeService.removeEmployee(0);
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(4);
        employeeService.removeEmployee(0);
        Assertions.assertThat(employees).hasSize(4);
    }
}
