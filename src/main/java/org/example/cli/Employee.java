package org.example.cli;

public class Employee {
    private int employee_id;
    private String name;
    private double salary;
    private String bank_account_number;
    private String national_insurance_number;

    public Employee(int employee_id, String name, double salary, String bank_account_number, String national_insurance_number) {
        this.employee_id = employee_id;
        this.name = name;
        this.salary = salary;
        this.bank_account_number = bank_account_number;
        this.national_insurance_number = national_insurance_number;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(String bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public String getNational_insurance_number() {
        return national_insurance_number;
    }

    public void setNational_insurance_number(String national_insurance_number) {
        this.national_insurance_number = national_insurance_number;
    }

}
