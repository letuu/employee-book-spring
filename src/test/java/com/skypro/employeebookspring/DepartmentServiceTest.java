package com.skypro.employeebookspring;

import com.skypro.employeebookspring.exception.EmployeeNotFoundExp;
import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.service.DepartmentService;
import com.skypro.employeebookspring.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("NameOne", "LastNameOne", 1, 10000),
            new Employee("NameTwo", "LastNameTwo", 1, 20000),
            new Employee("NameThree", "LastNameThree", 1, 30000),
            new Employee("NameFour", "LastNameFour", 2, 40000),
            new Employee("NameFive", "LastNameFive", 2, 50000)
    );
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        when(employeeService.getAllEmployees())
                .thenReturn(employees);
    }

    @Test
    void getEmployeesDepartment() {
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        Assertions.assertThat(employeeList)
                .hasSize(3)
                .contains(employees.get(0), employees.get(1), employees.get(2));
    }

    @Test
    void sumOfSalariesInDepartment() {
        int sum = this.departmentService.getSumOfSalariesInDepartment(1);
        Assertions.assertThat(sum).isEqualTo(60000);
    }

    @Test
    void maxSalariesInDepartment() {
        int max = this.departmentService.getMaxSalariesInDepartment(2);
        Assertions.assertThat(max).isEqualTo(50000);
    }

    @Test
    void minSalariesInDepartment() {
        int min = this.departmentService.getMinSalariesInDepartment(2);
        Assertions.assertThat(min).isEqualTo(40000);
    }

    @Test
    void groupedEmployeesByDepartments() {
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService.getEmployeesGroupedByDepartments();
        Assertions.assertThat(groupedEmployees)
                .hasSize(2)
                .containsKey(1)     //это не особо нужно
                .containsKey(2)
                .containsEntry(1, List.of(employees.get(0), employees.get(1), employees.get(2)))
                .containsEntry(2, List.of(employees.get(3), employees.get(4)));
    }

    @Test
    void whenNoEmployeesThenGroupByReturnEmptyMap() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService.getEmployeesGroupedByDepartments();
        Assertions.assertThat(groupedEmployees).isEmpty();
    }

    @Test
    void whenNoEmployeesThenMaxSalaryInDepartmentThrowsException() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> departmentService.getMaxSalariesInDepartment(0))
                .isInstanceOf(EmployeeNotFoundExp.class);
    }

    @Test
    void whenNoEmployeesThenMinSalaryInDepartmentThrowsException() {
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> departmentService.getMinSalariesInDepartment(0))
                .isInstanceOf(EmployeeNotFoundExp.class);
    }
}
