package org.example.resources;

import io.swagger.annotations.Api;
import org.example.api.EmployeeService;
import org.example.cli.DeliveryEmployeeProjectRequest;
import org.example.cli.DeliveryEmployeeRequest;
import org.example.cli.UpdateDeliveryEmployeeRequest;
import org.example.client.DeliveryEmployeeDoesNotExistException;
import org.example.client.FailedToDeleteEmployeeException;
import org.example.client.FailedToUpdateDeliveryEmployeeException;
import org.example.client.ProjectException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Api("Delivery Employee API")
@Path("/api")
public class DeliveryEmployeeController {
    EmployeeService deliveryEmployeeService = new EmployeeService();
    @GET
    @Path("/deliveryEmployees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees() {
        try {
            return Response.ok(deliveryEmployeeService.getAllDeliveryEmployees()).build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            return Response.serverError().build();
        }
    }

    @GET
    @Path("/deliveryEmployees/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeliveryEmployeeById(@PathParam("id") int id) {
        try {
            return Response.ok(deliveryEmployeeService.getDeliveryEmployeeById(id)).build();
        } catch(DeliveryEmployeeDoesNotExistException e){
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch(SQLException se){
            System.err.println(se.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @POST
    @Path("/deliveryEmployees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDeliveryEmployee(DeliveryEmployeeRequest employee){
        try{
            return Response.ok(deliveryEmployeeService.createDeliveryEmployee(employee)).build();
        }catch(ProjectException e){
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch(SQLException se){
            System.err.println(se.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/deliveryEmployees/{id}")
    @Produces
    public Response deleteDeliveryEmployee(@PathParam("id") int id) {
        try {
            deliveryEmployeeService.deleteDeliveryEmployee(id);

            return Response.ok().build();

        } catch (FailedToDeleteEmployeeException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();

        } catch (DeliveryEmployeeDoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @POST
    @Path("/addDeliveryEmployeeToProject")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDeliveryEmployeeToProject(List<DeliveryEmployeeProjectRequest> projects) {
        try{
            deliveryEmployeeService.assignDeliveryEmployeeToProject(projects);
            return Response.ok().build();
        }catch(SQLException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PUT
    @Path("/deliveryEmployees/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDeliveryEmployee(@PathParam("id") int id, UpdateDeliveryEmployeeRequest deliverEmployee) {
        try {
            deliveryEmployeeService.updateDeliveryEmployee(id, deliverEmployee);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ProjectException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (DeliveryEmployeeDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (FailedToUpdateDeliveryEmployeeException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
