package org.example.repo;

import com.mysql.cj.jdbc.CallableStatement;
import org.example.configuration.DB;
import org.example.entity.EmployeeDetails;
import org.example.entity.ProductsDetails;
import org.example.entity.response.ResponseData;
import org.example.utils.ResponseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Repository {
    Connection con;

    {
        try {
            con = DB.getCon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertInEmp(EmployeeDetails employeeDetails) {
        String query = """
                insert into employee_details(id, emp_fname, emp_lname, emp_phone, emp_job_title, emp_email) values(?,?,?,?,?,?)
                """;

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, employeeDetails.getId());
            ps.setString(2, employeeDetails.getFirstName());
            ps.setString(3, employeeDetails.getLastName());
            ps.setString(4, employeeDetails.getPhone());
            ps.setString(5, employeeDetails.getJobTitle());
            ps.setString(6, employeeDetails.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<EmployeeDetails> getAllEmp() {
        String query = """
                select * from employee_details
                """;
        List<EmployeeDetails> getList = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EmployeeDetails emp = new EmployeeDetails();
                emp.setId(rs.getInt("id"));
                emp.setFirstName(rs.getString("emp_fname"));
                emp.setLastName(rs.getString("emp_lname"));
                emp.setPhone(rs.getString("emp_phone"));
                emp.setJobTitle(rs.getString("emp_job_title"));
                emp.setEmail(rs.getString("emp_email"));
                getList.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getList;
    }

    public EmployeeDetails getByEmpId(int id) {
        String query = """
                select * from employee_details where id=?
                """;
        EmployeeDetails emp = new EmployeeDetails();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emp.setId(rs.getInt("id"));
                emp.setFirstName(rs.getString("emp_fname"));
                emp.setLastName(rs.getString("emp_lname"));
                emp.setPhone(rs.getString("emp_phone"));
                emp.setJobTitle(rs.getString("emp_job_title"));
                emp.setEmail(rs.getString("emp_email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }

    public void insertProducts(ProductsDetails productsDetails) {
        String query = """
                insert into product(product_id, emp_id, product_name) values (?,?,?)
                """;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, productsDetails.getProductId());
            ps.setInt(2, productsDetails.getEmpId());
            ps.setString(3, productsDetails.getProductName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ResponseData> getEmpProducts() {

        List<ResponseData> responseDataList = new ArrayList<>();

        String query = """
                SELECT e.id,
                       e.emp_fname,
                       e.emp_lname,
                       e.emp_phone,
                       e.emp_job_title,
                       e.emp_email,
                       p.product_name
                FROM employee_details e
                LEFT JOIN product p
                ON e.id = p.emp_id;
                """;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ResponseData responseData = new ResponseData();
                responseData.setId(rs.getInt("id"));
                responseData.setfName(rs.getString("emp_fname"));
                responseData.setlName(rs.getString("emp_lname"));
                responseData.setPhone(rs.getString("emp_phone"));
                responseData.setJotTitle(rs.getString("emp_job_title"));
                responseData.setEmail(rs.getString("emp_email"));
                responseData.setProductName(rs.getString("product_name"));

                responseDataList.add(responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseDataList;
    }

    public void updateEmp(int id, String jobTitle) {

        String query = """
                update employee_details
                set emp_job_title=?
                where id=?;
                """;

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, jobTitle);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteEmpById(int id) {
        String query = """
                delete from employee_details where id=?
                """;

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
