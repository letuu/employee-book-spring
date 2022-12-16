package com.skypro.employeebookspring.service;

import com.skypro.employeebookspring.exception.EmployeeNotFoundExp;
import com.skypro.employeebookspring.exception.InvalidEmployeeRequestExp;
import com.skypro.employeebookspring.exception.RepeatEmployeeException;
import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.record.EmployeeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
//        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
        if (!StringUtils.isAlpha(employeeRequest.getFirstName()) ||
                !StringUtils.isAlpha(employeeRequest.getLastName())) {
//            throw new IllegalArgumentException("Employee name should be set");
            throw new InvalidEmployeeRequestExp();
        }
        Employee employee = new Employee(StringUtils.capitalize(employeeRequest.getFirstName()),
                StringUtils.capitalize(employeeRequest.getLastName()),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());

        if (employees.containsValue(employee)) {
            throw new RepeatEmployeeException("Такой сотрудник уже существует");
        }
            this.employees.put(employee.getId(), employee);
            return employee;
    }

    public int getSalarySum() {
        return employees.values().stream().mapToInt(Employee::getSalary).sum();
    }

    public Employee getEmployeeMinSalary() {
        return employees.values().stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundExp());
    }

    public Employee getEmployeeMaxSalary() {
        return employees.values().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundExp::new);
    }

    public List<Employee> getEmployeeAboveAverageSalary() {
        Double averageSalary = getAverageSalary();
        if (averageSalary == null) {
            return Collections.emptyList();
        }
        return employees.values().stream()
                .filter(e -> e.getSalary() > averageSalary)
                .collect(Collectors.toList());
    }

    private Double getAverageSalary() {
        return employees.values().stream()
                .collect(Collectors.averagingInt(Employee::getSalary));
    }

//    private OptionalDouble getAverageSalary2() {
//        return employees.values().stream()
//                .mapToInt(Employee::getSalary).average();
//    }

    public Employee removeEmployee(int id) {
        return employees.remove(id);
    }
}
