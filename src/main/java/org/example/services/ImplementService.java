package org.example.services;

import org.example.entity.EmployeeDetails;
import org.example.entity.ProductsDetails;
import org.example.entity.response.ResponseData;
import org.example.repo.Repository;

import java.util.*;

public class ImplementService implements EmployeeService {
    private final Repository repo;

    public ImplementService(Repository repo) {
        this.repo = repo;
    }

    @Override
    public String insertInEmployee(EmployeeDetails employeeDetails) {
        if (employeeDetails == null ||
                (employeeDetails.getFirstName() == null || employeeDetails.getFirstName().trim().isEmpty()) &&
                        (employeeDetails.getLastName() == null || employeeDetails.getLastName().trim().isEmpty()) &&
                        (employeeDetails.getPhone() == null || employeeDetails.getPhone().trim().isEmpty()) &&
                        (employeeDetails.getJobTitle() == null || employeeDetails.getJobTitle().trim().isEmpty()) &&
                        (employeeDetails.getEmail() == null || employeeDetails.getEmail().trim().isEmpty())) {

            return "Employee details are missing or empty. Insert failed.";
        }

        repo.insertInEmp(employeeDetails);
        return "data has been inserted into the employees table";
    }

    @Override
    public List<EmployeeDetails> getAllEmployees() {
        return repo.getAllEmp();
    }

    @Override
    public EmployeeDetails getById(int id) {
        return repo.getByEmpId(id);
    }

    @Override
    public List<ResponseData> getEmpProducts() {
        return repo.getEmpProducts();
    }

    @Override
    public String insertInProducts(ProductsDetails productsDetails) {
        repo.insertProducts(productsDetails);
        return "data has been inserted into the products table";
    }

    @Override
    public String updateEmp(int id, String jobTitle) {
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            return "JobTitle is empty";
        }
        repo.updateEmp(id, jobTitle);
        return "data has been updated by id:" + id;
    }

    @Override
    public String deleteEmp(int id) {
        if (repo.deleteEmpById(id) == 0) {
            return "id not found in the db";
        }
        return "data has been deleted by id :" + id;
    }

}
