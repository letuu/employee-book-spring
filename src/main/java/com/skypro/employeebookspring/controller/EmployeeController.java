package com.skypro.employeebookspring.controller;

import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.record.EmployeeRequest;
import com.skypro.employeebookspring.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * HTTP методы
 * GET - Получение ресурса или набора ресурсов
 * POST - Создание ресурса
 * PUT - Модификация ресурса
 * PATCH - Частичная модификация ресурса
 * DELETE - Удаление ресурса
 */

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Collection<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return this.employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/employees/salary/sum")
    public int getSalarySum() {
        return this.employeeService.getSalarySum();
    }

    @GetMapping("/employees/salary/min")
    public Employee getEmployeeMinSalary() {
        return this.employeeService.getEmployeeMinSalary();
    }

    @GetMapping("/employees/salary/max")
    public Employee getEmployeeMaxSalary() {
        return this.employeeService.getEmployeeMaxSalary();
    }

    @GetMapping("/employees/highSalary")
    public List<Employee> getEmployeeAboveAverageSalary() {
        return this.employeeService.getEmployeeAboveAverageSalary();
    }
}
