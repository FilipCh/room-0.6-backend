package org.example.core;

import org.example.cli.DeliveryEmployeeRequest;
import org.example.cli.UpdateDeliveryEmployeeRequest;

public class DeliveryEmployeeValidator {
    public String isValid(DeliveryEmployeeRequest employee){
        if(employee.getBank_account_number().length() != 8){
            return "Bank account not of right length";
        }
        if(employee.getNational_insurance_number().length() != 9)
        {
            return "Error in NIN";
        }
        return null;
    }
    public String isValidUpdate(UpdateDeliveryEmployeeRequest employee) {
        if(employee.getBank_account_number().length() != 8){
            return "Bank account not of right length";
        }

        return null;
    }
}
