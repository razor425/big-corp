package com.rrodriguez.glide.bigcorp.converter;

import com.rrodriguez.glide.bigcorp.model.dao.EmployeeDAO;
import com.rrodriguez.glide.bigcorp.model.dao.OfficeDAO;
import com.rrodriguez.glide.bigcorp.model.dao.Transformable;
import com.rrodriguez.glide.bigcorp.model.dto.OfficeDTO;
import com.rrodriguez.glide.bigcorp.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OfficeConverter {

    @Autowired
    private OfficeService officeService;

    public List<OfficeDAO> getOfficesByIds(List<Long> ids) {
        List<OfficeDTO> officesRaw = officeService.getOfficesByIds(ids);

        return officesRaw.stream()
                .map(OfficeDAO::new)
                .collect(Collectors.toList());
    }

    public void complementOffice(List<EmployeeDAO> list) {
        List<Long> fetchIds = list.stream()
                .map(EmployeeDAO::getOffice)
                .filter(Objects::nonNull)
                .map(Transformable::getId)
                .collect(Collectors.toList());

        Map<Long, OfficeDAO> items = getOfficesByIds(fetchIds)
                .stream()
                .collect(Collectors.toMap(Transformable::getId, Function.identity()));

        for (EmployeeDAO employeeDAO : list) {
            if (employeeDAO.getOffice() != null)
                employeeDAO.setOffice(items.get(employeeDAO.getOffice().getId()));
        }
    }

    public OfficeDAO getOfficeById(Long id, List<Transformation> transformations) {
        Long[] ids = new Long[]{id};
        List<OfficeDAO> result = getOfficesByIds(Arrays.asList(ids));
        transformations(result);

        return result.get(0);
    }

    public List<OfficeDAO> getOffices(int limit, int offset, List<Transformation> transformations) {
        List<OfficeDAO> result = officeService.getOffices(limit, offset)
                .stream()
                .map(OfficeDAO::new)
                .collect(Collectors.toList());
        transformations(result);

        return result;
    }

    private void transformations(List<OfficeDAO> result) {
    }
}
