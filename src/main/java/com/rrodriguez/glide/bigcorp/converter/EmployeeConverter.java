package com.rrodriguez.glide.bigcorp.converter;

import com.rrodriguez.glide.bigcorp.controller.validation.Expansions;
import com.rrodriguez.glide.bigcorp.model.dao.DepartmentDAO;
import com.rrodriguez.glide.bigcorp.model.dao.EmployeeDAO;
import com.rrodriguez.glide.bigcorp.model.dto.EmployeeDTO;
import com.rrodriguez.glide.bigcorp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeConverter {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OfficeConverter officeConverter;

    @Autowired
    private DepartmentConverter departmentConverter;


    public EmployeeDAO getEmployeeById(Long id, List<Transformation> transformations) {
        Set<Long> ids = new HashSet<>();
        ids.add(id);
        List<EmployeeDAO> result = getEmployeesByIds(ids);

        result = transform(result, transformations);

        return result.get(0);
    }

    public List<EmployeeDAO> getEmployeesByIds(Set<Long> ids) {
        List<EmployeeDTO> employeesRaw = employeeService.getEmployeesById(ids);

        return employeesRaw.stream()
                .map(EmployeeDAO::new)
                .collect(Collectors.toList());
    }

    private List<EmployeeDAO> transform(List<EmployeeDAO> list, List<Transformation> transformations) {

        List<EmployeeDAO> employeeStage = list;
        List<DepartmentDAO> departmentStage = null;
        List<EmployeeDAO> employeeInterStage;

        for (Transformation t : transformations) {

            List<DepartmentDAO> departmentInterStage = null;
            employeeInterStage = null;

            for (Expansions expansion : t.getExpansions()) {

                switch (expansion) {
                    case EMPLOYEE_MANAGER:
                        employeeInterStage = this.complementManager(employeeStage);
                        break;

                    case EMPLOYEE_OFFICE:
                        officeConverter.complementOffice(employeeStage);
                        break;

                    case EMPLOYEE_DEPARTMENT:
                        departmentInterStage = departmentConverter.complementEmployee(employeeStage);
                        break;
                    case DEPARTMENT_SUPERDEPARTMENT:
                        departmentInterStage = departmentConverter.complementDepartment(departmentStage);
                }
            }

            employeeStage = employeeInterStage;
            departmentStage = departmentInterStage;
        }

        return list;
    }

    private List<EmployeeDAO> complementManager(List<EmployeeDAO> list) {
        Set<Long> fetchIds = list.stream()
                .map(EmployeeDAO::getManager)
                .filter(Objects::nonNull)
                .map(EmployeeDAO::getId)
                .collect(Collectors.toSet());

        Map<Long, EmployeeDAO> items = getEmployeesByIds(fetchIds)
                .stream()
                .collect(Collectors.toMap(EmployeeDAO::getId, Function.identity()));

        for (EmployeeDAO employeeDAO : list) {
            if (employeeDAO.getManager() != null)
                employeeDAO.setManager(items.get(employeeDAO.getManager().getId()));
        }
        return new ArrayList<>(items.values());
    }

    public List<EmployeeDAO> getEmployees(int limit, int offset, List<Transformation> transformations) {
        List<EmployeeDAO> result = employeeService.getEmployees(limit, offset)
                .stream()
                .map(EmployeeDAO::new)
                .collect(Collectors.toList());

        transform(result, transformations);

        return result;
    }


}
