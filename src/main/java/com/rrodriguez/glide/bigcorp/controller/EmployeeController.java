package com.rrodriguez.glide.bigcorp.controller;

import com.rrodriguez.glide.bigcorp.controller.validation.InputValidator;
import com.rrodriguez.glide.bigcorp.converter.EmployeeConverter;
import com.rrodriguez.glide.bigcorp.converter.Transformation;
import com.rrodriguez.glide.bigcorp.exception.InvalidExpansionException;
import com.rrodriguez.glide.bigcorp.model.dao.EmployeeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
public class EmployeeController {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeConverter employeeService;

    @GetMapping("/employees/{id}")
    public ResponseEntity getEmployeeById(
            @PathVariable Long id,
            @RequestParam(value = "expand", required = false) List<String> expand) {
        List<Transformation> transformations;
        try {
            transformations = InputValidator.validateAndParseExpansions("employee", expand);
        } catch (InvalidExpansionException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(employeeService.getEmployeeById(id, transformations));
    }


    //TODO make spring return 400 bad request instead of 500 internal server error
    @GetMapping("/employees")
    public ResponseEntity getEmployee(
            @RequestParam(value = "limit", required = false, defaultValue = "100") @Min(1) @Max(1000) int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") @Min(0) int offset,
            @RequestParam(value = "expand", required = false) List<String> expand) {
        List<Transformation> transformations;

        try {
            transformations = InputValidator.validateAndParseExpansions("employee", expand);
        } catch (InvalidExpansionException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(employeeService.getEmployees(limit, offset,transformations));
    }

}
