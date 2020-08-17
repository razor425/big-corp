package com.rrodriguez.glide.bigcorp.controller;

import com.rrodriguez.glide.bigcorp.controller.validation.InputValidator;
import com.rrodriguez.glide.bigcorp.converter.OfficeConverter;
import com.rrodriguez.glide.bigcorp.converter.Transformation;
import com.rrodriguez.glide.bigcorp.exception.InvalidExpansionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class OfficeController {

    @Autowired
    private OfficeConverter officeService;

    @GetMapping("/offices/{id}")
    public ResponseEntity getOfficeById(
            @PathVariable Long id,
            @RequestParam(value = "expand", required = false) List<String> expand) {
        List<Transformation> transformations;
        try {
            transformations = InputValidator.validateAndParseExpansions("office", expand);
        } catch (InvalidExpansionException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(officeService.getOfficeById(id, transformations));
    }

    @GetMapping("/offices")
    public ResponseEntity getOffices(
            @RequestParam(value = "limit", required = false, defaultValue = "100") @Min(1) @Max(1000) int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") @Min(0) int offset,
            @RequestParam(value = "expand", required = false) List<String> expand) {

        List<Transformation> transformations;
        try {
            transformations = InputValidator.validateAndParseExpansions("office", expand);
        } catch (InvalidExpansionException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(officeService.getOffices(limit, offset, transformations));
    }


}
