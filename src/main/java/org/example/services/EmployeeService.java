package org.example.services;

import org.example.entity.EmployeeDetails;
import org.example.entity.ProductsDetails;
import org.example.entity.response.ResponseData;

import java.util.*;

public interface EmployeeService {
    String insertInEmployee(EmployeeDetails employeeDetails);

    List<EmployeeDetails> getAllEmployees();

    EmployeeDetails getById(int id);

    List<ResponseData> getEmpProducts();

    String insertInProducts(ProductsDetails productsDetails);

    String updateEmp(String id, String jobTitle);

    String deleteEmp(int id);
}
