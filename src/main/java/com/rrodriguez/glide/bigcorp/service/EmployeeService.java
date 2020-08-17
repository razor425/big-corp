package com.rrodriguez.glide.bigcorp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.rrodriguez.glide.bigcorp.exception.EmployeeApiException;
import com.rrodriguez.glide.bigcorp.model.dto.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Value("${employees.api.url.base}")
    private String baseURL;

    private final RestTemplate restTemplate;

    private static final ObjectMapper objectMapper = JsonMapper.builder().build();


    public EmployeeService(@Qualifier("restTemplateEmployees") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EmployeeDTO> getEmployeesById(Set<Long> ids) {
        List<EmployeeDTO> result = new ArrayList<>();
        String params = ids.stream().map(p -> "id=" + p.toString()).collect(Collectors.joining("&"));
        return !params.equalsIgnoreCase("") ? request(params) : result;
    }

    public List<EmployeeDTO> getEmployees(int limit, int offset) {
        String params = String.format("limit=%s&offset=%s", limit, offset);
        return request(params);
    }

    public List<EmployeeDTO> request(String params) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeDTO> entity = new HttpEntity<>(headers);

        String requestUrl = baseURL + "?" + params;

        LOGGER.info("REQUESTING {}", requestUrl);

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        try {
            return objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<List<EmployeeDTO>>() {
                    });
        } catch (IOException e) {
            throw new EmployeeApiException("Error in body, could not parse", e);
        }
    }

}
