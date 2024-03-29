package com.example.services.schema.url;

import com.example.services.schema.SchemaServiceFactory;
import com.example.services.schema.SchemaServiceRequest;
import com.example.services.schema.UrlParser;
import com.example.services.schema.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractUrlParserTest {
    protected final String jokeApiKey = "9728c23d120f4d1985ff2a7cc019bd96";
    protected Map<String, Object> schema;
    protected Map<String, String> parameters;

    @BeforeEach
    public void setServiceName() {
        try {
            String schemaSource = Util.readFile("joke", false);
            schema = Util.JSONtoMap(schemaSource);
            parameters = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void checkIO(String serviceName, Map<String, String> parameters, String expectedUrl) throws IOException {
        SchemaServiceRequest service = SchemaServiceFactory.getService(serviceName,
                (HashMap<String, String>) parameters);
        String url = service.getURL();
        assertEquals(expectedUrl, url);
    }

    protected void checkSchema(Map<String, String> parameters, String expectedUrl) {
        String url = UrlParser.parse(schema, parameters);
        assertEquals(expectedUrl, url);
    }
}
