package com.rrodriguez.glide.bigcorp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.rrodriguez.glide.bigcorp.model.dto.OfficeDTO;
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
public class OfficeService {

    @Value("${offices.path}")
    private String jsonOfficePath;
    private static final ObjectMapper objectMapper = JsonMapper.builder().build();

    private Map<Long, OfficeDTO> officesMap;
    private List<OfficeDTO> officesList;

    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:" + jsonOfficePath);

        List<OfficeDTO> offices = objectMapper.readValue(file,
                new TypeReference<List<OfficeDTO>>() {
                });

        this.officesList = offices;
        this.officesMap = new HashMap<>();

        for (OfficeDTO office : offices) {
            officesMap.put(office.getId(), office);
        }
    }

    public OfficeDTO getOfficeById(long id) {
        return officesMap.getOrDefault(id, null);
    }

    public List<OfficeDTO> getOfficesByIds(List<Long> ids) {
        return officesMap.entrySet()
                .stream()
                .filter(p -> ids.contains(p.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public List<OfficeDTO> getOffices(int limit, int offset) {

        List<OfficeDTO> response = new ArrayList<>();

        if (offset < officesList.size()) {
            int offsetAndLimit = offset + limit;
            int upperBound = officesList.size() > offsetAndLimit ? offsetAndLimit : officesList.size();

            response = officesList.subList(offset, upperBound);

        }
        return response;
    }
}
