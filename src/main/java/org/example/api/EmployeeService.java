package org.example.api;

import org.example.cli.Employee;
import org.example.cli.DeliveryEmployeeProjectRequest;
import org.example.cli.DeliveryEmployeeRequest;
import org.example.client.DeliveryEmployeeDoesNotExistException;
import org.example.client.FailedToDeleteEmployeeException;
import org.example.client.ProjectException;
import org.example.core.DeliveryEmployeeValidator;
import org.example.db.EmployeeDao;
import org.example.db.ProjectDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    EmployeeDao employeeDao = new EmployeeDao();
    DeliveryEmployeeValidator deliveryEmployeeValidator = new DeliveryEmployeeValidator();
    ProjectDao projectDao = new ProjectDao();

    public List<Employee> getAllDeliveryEmployees() throws SQLException {
        return employeeDao.getAllDeliveryEmployees();
    }
    public int createDeliveryEmployee(DeliveryEmployeeRequest employee) throws ProjectException, SQLException {
            String validation = deliveryEmployeeValidator.isValid(employee);
            if(validation != null){
                throw new ProjectException();
            }
            int id = employeeDao.createDeliveryEmployee(employee);

            if(id == -1){
                throw new ProjectException();
            }
            return id;

    }
    public void assignDeliveryEmployeeToProject(List<DeliveryEmployeeProjectRequest> requests) throws SQLException {
        List<DeliveryEmployeeProjectRequest> validRequests = new ArrayList<>();

        for(DeliveryEmployeeProjectRequest request : requests){
            if(employeeDao.deliveryEmployeeExists(request.getDelivery_employee_id()) && projectDao.projectExists(request.getProject_id())){
                validRequests.add(request);
            }
        }
        if(!validRequests.isEmpty()) {
            employeeDao.assignDeliveryEmployeeToProject(validRequests);
        }
    }

    public Employee getDeliveryEmployeeById (int id) throws SQLException, DeliveryEmployeeDoesNotExistException {
            Employee employee = employeeDao.getDeliveryEmployeeById(id);

                if(employee == null) {
                    throw new DeliveryEmployeeDoesNotExistException();
            }

            return employee;

    }

    public void deleteDeliveryEmployee(int id) throws FailedToDeleteEmployeeException, DeliveryEmployeeDoesNotExistException {
        try {
            Employee employeeToDelete = employeeDao.getDeliveryEmployeeById(id);

               if (employeeToDelete == null){
                   throw new DeliveryEmployeeDoesNotExistException();
               }
                employeeDao.deleteDeliveryEmployee(id);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToDeleteEmployeeException();
        }
    }

}
