package org.example.client;

public class FailedToGenerateTokenException extends Exception {
    @Override
    public String getMessage(){
        return "Failed to generate token";
    }
}
