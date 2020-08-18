package com.rrodriguez.glide.bigcorp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rrodriguez.glide.bigcorp.exception.InvalidExpansionException;
import com.rrodriguez.glide.bigcorp.model.deserializer.EmployeeDeserializer;
import com.rrodriguez.glide.bigcorp.model.dto.CustomEmployeeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EmployeeControllerTest {

    @LocalServerPort
    private int port = 8080;

    private static final ObjectMapper objectMapper = JsonMapper.builder().build();
    static{
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CustomEmployeeDTO.class, new EmployeeDeserializer());
        objectMapper.registerModule(module);
    }

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();
    @Test
    public void testGetEmployeeById() throws InvalidExpansionException{
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/employees"+"/1"),
                HttpMethod.GET,
                entity,
                String.class);

        CustomEmployeeDTO result;
        try {
             result = objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<CustomEmployeeDTO>() {
                    });

            System.out.println(result.getId());
            System.out.println(result.getFirst());
            System.out.println(result.getLast());
            System.out.println(result.getManager());

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:"+port+uri;
    }
}
