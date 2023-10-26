package org.example.client;

public class FailedToUpdateDeliveryEmployeeException extends Exception {
    @Override
    public String getMessage(){
        return "Failed to update delivery employee";
    }
}
