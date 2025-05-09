package org.example.entity.response;

import org.example.entity.EmployeeDetails;

import java.util.List;

public class EmployeesDetails {
    private List<EmployeeDetails> employeeDetails;

    public EmployeesDetails(List<EmployeeDetails> employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public List<EmployeeDetails> getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(List<EmployeeDetails> employeeDetails) {
        this.employeeDetails = employeeDetails;
    }
}
