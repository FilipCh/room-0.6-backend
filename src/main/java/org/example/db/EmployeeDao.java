package org.example.db;

import org.example.cli.Employee;
import org.example.cli.DeliveryEmployeeProjectRequest;
import org.example.cli.DeliveryEmployeeRequest;
import org.example.cli.UpdateDeliveryEmployeeRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

     DatabaseConnector databaseConnector = new DatabaseConnector();
    public List<Employee> getAllDeliveryEmployees() throws SQLException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT `employee_id`," +
                "`name`," +
                "`salary`," +
                "`bank_account_number`," +
                "`national_insurance_number`" +
                "FROM `Employee`;");

        List<Employee> emoloyeeList = new ArrayList<>();

        while (rs.next()) {
            Employee employee = new Employee(
                    rs.getInt("employee_id"),
                    rs.getString("name"),
                    rs.getDouble("salary"),
                    rs.getString("bank_account_number"),
                    rs.getString("national_insurance_number")
            );
            emoloyeeList.add(employee);
        }

        return emoloyeeList;

    }
    public int createDeliveryEmployee(DeliveryEmployeeRequest employee) throws SQLException{
        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO `Employee` (`name`, `salary`, `bank_account_number`, `national_insurance_number`) VALUES (?,?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement. RETURN_GENERATED_KEYS);

        st.setString(1, employee.getName());
        st.setDouble(2, employee.getSalary());
        st.setString(3, employee.getBank_account_number());
        st.setString(4, employee.getNational_insurance_number());


        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }
    public void assignDeliveryEmployeeToProject(List<DeliveryEmployeeProjectRequest> requests) throws SQLException{
        Connection c = databaseConnector.getConnection();

        c.setAutoCommit(false);

        String insertStatement = "INSERT INTO `Delivery_EmployeeProject` (`project_id`, `employee_id`, `is_Active`, `created_On`, `updated_On`) VALUES (?,?,?,?,?)";
        PreparedStatement st = c.prepareStatement(insertStatement, Statement. RETURN_GENERATED_KEYS);

        for(DeliveryEmployeeProjectRequest request : requests){
            st.setInt(1, request.getProject_id());
            st.setInt(2, request.getDelivery_employee_id());
            st.setBoolean(3, request.isIs_Active());
            st.setDate(4, request.getCreated_On());
            st.setDate(5, request.getUpdated_On());
            st.addBatch();
        }

        st.executeBatch();

        c.commit();

        c.close();
    }
    public boolean deliveryEmployeeExists(int employeeId) throws SQLException {
        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT employee_id"  +
                " FROM `Employee` where employee_id=" + employeeId);

        if(rs.next())
        {
            return true;
        }
        return false;
    }

    public void deleteDeliveryEmployee(int id) throws SQLException {

        Connection c = databaseConnector.getConnection();

        String deleteStatement = "DELETE from Employee WHERE employee_id = ? ";

        PreparedStatement st = c.prepareStatement(deleteStatement);

        st.setInt(1, id);

        st.executeUpdate();
    }

    public Employee getDeliveryEmployeeById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        String selectStatement  = "SELECT employee_id, `name`, salary, bank_account_number, national_insurance_number FROM Employee WHERE employee_id = ?;";

        PreparedStatement st = c.prepareStatement(selectStatement);

        st.setInt(1, id);

        ResultSet rs = st.executeQuery();

        while(rs.next()) {
            return new Employee(
                    rs.getInt("employee_id"),
                    rs.getString("name"),
                    rs.getDouble("salary"),
                    rs.getString("bank_account_number"),
                    rs.getString("national_insurance_number")
            );
        }
        return null;
    }
    public void updateDeliveryEmployee(int id, UpdateDeliveryEmployeeRequest deliveryEmployee) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String updateStatement = "UPDATE `Employee` SET `name` = ?, `salary` = ?, `bank_account_number` = ? WHERE `employee_id` = ?;";

        PreparedStatement st = c.prepareStatement(updateStatement);

        st.setString(1, deliveryEmployee.getName());
        st.setDouble(2, deliveryEmployee.getSalary());
        st.setString(3, deliveryEmployee.getBank_account_number());
        st.setInt(4, id);

        st.executeUpdate();
    }
}





