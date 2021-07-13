package com.example.api.controllers;

import com.example.api.ApiLogger;
import com.example.api.ServiceApiRequest;
import com.example.api.responses.ServiceResponse;
import com.example.api.responses.UnknownServiceResponse;
import com.example.handler.ServiceFactory;
import com.example.services.AbstractServiceRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Contains endpoint logic to perform service request
 */
@RestController
@RequestMapping("/api")
public class ServiceController {

    /**
     * Convert the keys and values in a map to uppercase.
     *
     * @param payload the map to convert.
     * @return the map with uppercase keys and values.
     */
    public HashMap<String, String> toUpperCase(Map<String, String> payload) {
        HashMap<String, String> params = new HashMap<>();
        for (Map.Entry<String, String> entry : payload.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            params.put(key.toUpperCase(), value);
        }
        return params;
    }

    /**
     * Call an external service API with parameters and return its response.
     *
     * @param headers the request headers.
     * @param service the name of the service to call.
     * @param payload the URL parameters.
     * @return the response from the service.
     */
    @GetMapping("/{service}")
    public ServiceResponse service(@RequestHeader Map<String, String> headers,
            @PathVariable String service, @RequestParam Map<String, String> payload) {
        service = service.replace("-", " ");
        HashMap<String, String> requestParams = toUpperCase(payload);
        ApiLogger.logApiRequest(service, headers, requestParams);
        AbstractServiceRequest serviceRequest = ServiceFactory.getServiceRequestByName(service, requestParams);
        if (serviceRequest != null) {
            ServiceApiRequest apiRequest = new ServiceApiRequest(service, serviceRequest);
            ServiceResponse response = apiRequest.perform();
            ApiLogger.logApiCall(apiRequest, response);
            return response;
        }
        return new UnknownServiceResponse(service);
    }
}
