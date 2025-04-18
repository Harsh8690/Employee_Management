package org.example.services;

import org.example.entity.EmployeeDetails;
import org.example.entity.ProductsDetails;
import org.example.entity.response.ResponseData;
import org.example.repo.Repository;

import java.util.List;

public class ImplementService implements EmployeeService {
    private final Repository repo;

    public ImplementService(Repository repo) {
        this.repo = repo;
    }

    @Override
    public String insertInEmployee(EmployeeDetails employeeDetails) {
        repo.insertInEmp(employeeDetails);
        return "inserted into the employees";
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
        return "inserted into products";
    }

    @Override
    public String updateEmp(int id, String jobTitle) {
        repo.updateEmp(id, jobTitle);
        return "updated data in employee";
    }

    @Override
    public String deleteEmp(int id) {
        repo.deleteEmpById(id);
        return "deleted row by id :" + id;
    }

}
