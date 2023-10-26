package org.example.client;

public class FailedToLoginException extends Exception {
    @Override
    public String getMessage(){
        return "Login failed";
    }
}
