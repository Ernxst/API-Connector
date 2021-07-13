package com.example.api.responses;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class UnknownServiceResponse extends ServiceResponse {
    public UnknownServiceResponse(String service) {
        super(service, "Unknown service name: '" + service + "'", new HashMap<>(), HttpStatus.NOT_FOUND.value());
    }
}
