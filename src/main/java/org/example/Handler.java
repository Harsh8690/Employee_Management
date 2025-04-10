package org.example;

import java.util.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerRequestEvent;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerResponseEvent;
import com.google.gson.Gson;
import org.example.entity.EmployeeDetails;
import org.example.entity.ProductsDetails;
import org.example.entity.response.ResponseData;
import org.example.repo.Repository;
import org.example.services.EmployeeService;
import org.example.services.ImplementService;
import org.example.utils.ResponseUtils;


public class Handler implements RequestHandler<ApplicationLoadBalancerRequestEvent, ApplicationLoadBalancerResponseEvent> {

    private final EmployeeService services;

    public Handler(EmployeeService services) {
        this.services = new ImplementService(new Repository());
    }

    @Override
    public ApplicationLoadBalancerResponseEvent handleRequest(ApplicationLoadBalancerRequestEvent event, Context context) {

        if (!List.of("GET", "POST", "PUT", "DELETE").contains(event.getHttpMethod())) {
            return ResponseUtils.notFoundResponse("HTTP METHOD NOT FOUND", 404, "404 ERROR");
        }

        switch (event.getHttpMethod()) {
            case "POST":
                if (event.getPath().contains("/addEmp")) {
                    Gson gson = new Gson();
                    EmployeeDetails employeeDetails = gson.fromJson(event.getBody(), EmployeeDetails.class);
                    return ResponseUtils.response(services.insertInEmployee(employeeDetails), 200, "200 OK", new HashMap<>());

                } else if (event.getPath().contains("/products/addProduct")) {
                    Gson gson = new Gson();
                    ProductsDetails productsDetails = gson.fromJson(event.getBody(), ProductsDetails.class);
                    return ResponseUtils.response(services.insertInProducts(productsDetails), 200, "200 OK", new HashMap<>());
                }
                break;

            case "GET":
                if (event.getPath().contains("/employees/getAllEmp")) {
                    Gson gson = new Gson();
                    List<EmployeeDetails> service = services.getAllEmployees();
                    return ResponseUtils.response(gson.toJson(service), 200, "200 OK", new HashMap<>());

                } else if (event.getPath().startsWith("/employees/getEmp/")) {
                    Gson gson = new Gson();
                    int id = Integer.parseInt(event.getPath().substring("/employees/getEmp/".length()));
                    EmployeeDetails employeeDetails = services.getById(id);
                    return ResponseUtils.response(gson.toJson(employeeDetails), 200, "200 OK", new HashMap<>());

                } else if (event.getPath().contains("/getAssignEmpProducts")) {
                    Gson gson = new Gson();
                    List<ResponseData> responseData = services.getEmpProducts();
                    return ResponseUtils.response(gson.toJson(responseData), 200, "200 OK", new HashMap<>());
                }
                break;

            case "PUT":
                if (event.getPath().startsWith("/emp/update/")) {
                    int id = Integer.parseInt(event.getPath().substring("/emp/update/".length()));
                    Gson gson = new Gson();
                    EmployeeDetails employeeDetails = gson.fromJson(event.getBody(), EmployeeDetails.class);
                    return ResponseUtils.response(services.updateEmp(id, employeeDetails.getJobTitle()), 200, "200 OK", new HashMap<>());
                }
                break;

            case "DELETE":
                if (event.getPath().contains("/emp/delete/")) {
                    int id = Integer.parseInt(event.getPath().substring("/emp/delete/".length()));
                    return ResponseUtils.response(services.deleteEmp(id), 200, "200 OK", new HashMap<>());
                }
                break;
        }
        return ResponseUtils.notFoundResponse("HTTP METHOD NOT FOUND", 404, "404 ERROR");
    }
}
