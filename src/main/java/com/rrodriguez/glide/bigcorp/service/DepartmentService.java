package com.rrodriguez.glide.bigcorp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.rrodriguez.glide.bigcorp.model.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Value("${departments.path}")
    private String jsonDepartmentsPath;
    private static final ObjectMapper objectMapper = JsonMapper.builder().build();

    private Map<Long, DepartmentDTO> departmentsMap;
    private List<DepartmentDTO> departmentList;

    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:" + jsonDepartmentsPath);

        List<DepartmentDTO> departments = objectMapper.readValue(file, new TypeReference<List<DepartmentDTO>>() {
        });

        this.departmentsMap = new HashMap<>();
        this.departmentList = departments;

        for (DepartmentDTO department : departments) {
            departmentsMap.put(department.getId(), department);
        }
    }

    public DepartmentDTO getDepartmentById(long id) {
        return departmentsMap.getOrDefault(id, null);
    }

    public List<DepartmentDTO> getDepartmentsByIds(List<Long> ids) {
        return departmentsMap.entrySet()
                .stream()
                .filter(p -> ids.contains(p.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public List<DepartmentDTO> getDepartments(int limit, int offset) {

        List<DepartmentDTO> response = new ArrayList<>();

        if (offset < departmentList.size()) {
            int offsetAndLimit = offset + limit;
            int upperBound = departmentList.size() > offsetAndLimit ? offsetAndLimit : departmentList.size();

            response = departmentList.subList(offset, upperBound);

        }
        return response;
    }
}
