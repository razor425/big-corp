package com.rrodriguez.glide.bigcorp.converter;

import com.rrodriguez.glide.bigcorp.controller.validation.Expansions;
import com.rrodriguez.glide.bigcorp.model.dao.DepartmentDAO;
import com.rrodriguez.glide.bigcorp.model.dao.EmployeeDAO;
import com.rrodriguez.glide.bigcorp.model.dao.Transformable;
import com.rrodriguez.glide.bigcorp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DepartmentConverter {

    @Autowired
    private DepartmentService departmentService;

    private List<DepartmentDAO> getDepartmentsByIds(List<Long> ids) {
        return departmentService.getDepartmentsByIds(ids)
                .stream()
                .map(DepartmentDAO::new)
                .collect(Collectors.toList());
    }

    public DepartmentDAO getDepartmentById(Long id, List<Transformation> transformations) {
        Long[] array = new Long[]{id};
        List<DepartmentDAO> result = getDepartmentsByIds(Arrays.asList(array));
        transform(result, transformations);

        return result.get(0);
    }

    public List<DepartmentDAO> getDepartments(int limit, int offset, List<Transformation> transformations) {
        List<DepartmentDAO> list = departmentService.getDepartments(limit, offset)
                .stream()
                .map(DepartmentDAO::new)
                .collect(Collectors.toList());

        transform(list, transformations);
        return list;
    }

    private void transform(List<DepartmentDAO> list, List<Transformation> transformations) {
        List<DepartmentDAO> stage = list;

        for (Transformation t : transformations) {
            for (Expansions expansion : t.getExpansions()) {
                stage = complementDepartment(stage);
            }
        }
    }

    public List<DepartmentDAO> complementDepartment(List<DepartmentDAO> list) {

        List<Long> fetchIds = list.stream()
                .map(DepartmentDAO::getSuperdepartment)
                .filter(Objects::nonNull)
                .map(Transformable::getId)
                .collect(Collectors.toList());

        Map<Long, DepartmentDAO> items = getDepartmentsByIds(fetchIds)
                .stream()
                .collect(Collectors.toMap(Transformable::getId, Function.identity()));

        for (DepartmentDAO departmentDAO : list) {
            if (departmentDAO.getSuperdepartment() != null)
                departmentDAO.setSuperdepartment(items.get(departmentDAO.getSuperdepartment().getId()));
        }
        return new ArrayList<>(items.values());
    }

    public List<DepartmentDAO> complementEmployee(List<EmployeeDAO> list) {

        List<Long> fetchIds = list.stream()
                .map(EmployeeDAO::getDepartment)
                .filter(Objects::nonNull)
                .map(Transformable::getId)
                .collect(Collectors.toList());

        Map<Long, DepartmentDAO> items = getDepartmentsByIds(fetchIds)
                .stream()
                .collect(Collectors.toMap(Transformable::getId, Function.identity()));

        for (EmployeeDAO employeeDAO : list) {
            if (employeeDAO.getDepartment() != null)
                employeeDAO.setDepartment(items.get(employeeDAO.getDepartment().getId()));
        }
        return new ArrayList<>(items.values());
    }
}
