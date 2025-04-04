package org.example;

import java.util.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerRequestEvent;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerResponseEvent;
import com.google.gson.Gson;
import org.example.entity.EmployeeDetails;
import org.example.entity.ProductsDetails;
import org.example.repo.Repository;
import org.example.services.EmployeeService;
import org.example.services.ImplementService;
import org.example.utils.ResponseUtils;


public class Handler implements RequestHandler<ApplicationLoadBalancerRequestEvent, ApplicationLoadBalancerResponseEvent> {

    private EmployeeService services;

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
                    return insertEmp(event);
                } else if (event.getPath().contains("/products/addProduct")) {
                    return insertProducts(event);
                }
                break;

            case "GET":
                if (event.getPath().contains("/employees/getAllEmp")) {
                    return getAllEmployees(event);
                } else if (event.getPath().startsWith("/employees/getEmp/")) {
                    return getByIdEmp(event);
                } else if (event.getPath().contains("/getAssignEmpProducts")) {
                    return getAssignEmpProducts(event);
                }
                break;

            case "PUT":
                if (event.getPath().startsWith("/emp/update/")) {
                    return empUpdate(event);
                }
                break;

            case "DELETE":
                if (event.getPath().contains("/emp/delete/")) {
                    return deleteEmp(event);
                }
                break;
        }
        return ResponseUtils.notFoundResponse("HTTP METHOD NOT FOUND", 404, "404 ERROR");
    }

    private ApplicationLoadBalancerResponseEvent insertEmp(ApplicationLoadBalancerRequestEvent event) {
        Gson gson = new Gson();
        EmployeeDetails employeeDetails = gson.fromJson(event.getBody(), EmployeeDetails.class);
        return ResponseUtils.response(services.insertInEmployee(employeeDetails), 200, "200 OK", new HashMap<>());
    }

    private ApplicationLoadBalancerResponseEvent insertProducts(ApplicationLoadBalancerRequestEvent event) {
        Gson gson = new Gson();
        ProductsDetails productsDetails = gson.fromJson(event.getBody(), ProductsDetails.class);
        return ResponseUtils.response(services.insertInProducts(productsDetails), 200, "200 OK", new HashMap<>());
    }

    private ApplicationLoadBalancerResponseEvent getAllEmployees(ApplicationLoadBalancerRequestEvent event) {
        Gson gson = new Gson();
        List<EmployeeDetails> service=services.getAllEmployees();
        return ResponseUtils.response(gson.toJson(service), 200, "200 OK", new HashMap<>());
    }

    private ApplicationLoadBalancerResponseEvent getByIdEmp(ApplicationLoadBalancerRequestEvent event) {
        Gson gson = new Gson();
        int id = Integer.parseInt(event.getPath().substring("/employees/getEmp/".length()));
        EmployeeDetails employeeDetails=services.getById(id);
        return ResponseUtils.response(gson.toJson(employeeDetails), 200, "200 OK", new HashMap<>());
    }

    private ApplicationLoadBalancerResponseEvent getAssignEmpProducts(ApplicationLoadBalancerRequestEvent event) {
        Gson gson = new Gson();
        return ResponseUtils.response(gson.toJson(services.getEmpProducts()), 200, "200 OK", new HashMap<>());
    }

    private ApplicationLoadBalancerResponseEvent empUpdate(ApplicationLoadBalancerRequestEvent event) {
        int id = Integer.parseInt(event.getPath().substring("/emp/update/".length()));
        Gson gson = new Gson();
        EmployeeDetails employeeDetails = gson.fromJson(event.getBody(), EmployeeDetails.class);
        return ResponseUtils.response(services.updateEmp(id, employeeDetails.getJobTitle()), 200, "200 OK", new HashMap<>());
    }

    private ApplicationLoadBalancerResponseEvent deleteEmp(ApplicationLoadBalancerRequestEvent event) {
        int id = Integer.parseInt(event.getPath().substring("/emp/delete/".length()));
        return ResponseUtils.response(services.deleteEmp(id), 200, "200 OK", new HashMap<>());
    }

}
