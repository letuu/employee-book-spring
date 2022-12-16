package com.skypro.employeebookspring.service;

import com.skypro.employeebookspring.exception.EmployeeNotFoundExp;
import com.skypro.employeebookspring.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Collection<Employee> getDepartmentEmployees(int department) {
        return getEmployeesByDepartmentStream(department)
                .collect(Collectors.toList());
    }

    public int getSumOfSalariesInDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int getMaxSalariesInDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .max()
                .orElseThrow(EmployeeNotFoundExp::new);
    }

    public int getMinSalariesInDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow(EmployeeNotFoundExp::new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartments() {
        return employeeService.getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    private Stream<Employee> getEmployeesByDepartmentStream(int department) {
        return employeeService.getAllEmployees()
                .stream()
                .filter(e -> e.getDepartment() == department);
    }
}
