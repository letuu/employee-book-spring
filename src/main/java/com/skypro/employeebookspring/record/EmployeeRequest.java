package com.skypro.employeebookspring.record;

public class EmployeeRequest {

    private String firstName;
    private String lastName;
    private int department;
    private int salary;

    public EmployeeRequest(String firstName, String lastName, int department, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
