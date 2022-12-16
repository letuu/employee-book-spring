package com.skypro.employeebookspring.controller;

import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}/employees")
    public Collection<Employee> getDepartmentEmployees(@PathVariable("id") int department) {
        return this.departmentService.getDepartmentEmployees(department);
    }

    @GetMapping("/{id}/salary/sum")
    public int getSumOfSalariesInDepartment(@PathVariable("id") int department) {
        return this.departmentService.getSumOfSalariesInDepartment(department);
    }

    @GetMapping("/{id}/salary/max")
    public int getMaxSalariesInDepartment(@PathVariable("id") int department) {
        return this.departmentService.getMaxSalariesInDepartment(department);
    }

    @GetMapping("/{id}/salary/min")
    public int getMinSalariesInDepartment(@PathVariable("id") int department) {
        return this.departmentService.getMinSalariesInDepartment(department);
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartments() {
        return this.departmentService.getEmployeesGroupedByDepartments();
    }
}
