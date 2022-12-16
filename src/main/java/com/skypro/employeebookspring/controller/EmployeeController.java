package com.skypro.employeebookspring.controller;

import com.skypro.employeebookspring.exception.InvalidEmployeeRequestExp;
import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.record.EmployeeRequest;
import com.skypro.employeebookspring.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Collection<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

//    @PostMapping
//    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
//        return this.employeeService.addEmployee(employeeRequest);
//    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            return ResponseEntity.ok(this.employeeService.addEmployee(employeeRequest));
        } catch (InvalidEmployeeRequestExp e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();  //вернет 400 ошибку, можно передать body(null) вместо build()
        }
    }

    @GetMapping("/salary/sum")
    public int getSalarySum() {
        return this.employeeService.getSalarySum();
    }

    @GetMapping("/salary/min")
    public Employee getEmployeeMinSalary() {
        return this.employeeService.getEmployeeMinSalary();
    }

    @GetMapping("/salary/max")
    public Employee getEmployeeMaxSalary() {
        return this.employeeService.getEmployeeMaxSalary();
    }

    @GetMapping("/highSalary")
    public List<Employee> getEmployeeAboveAverageSalary() {
        return this.employeeService.getEmployeeAboveAverageSalary();
    }
}
