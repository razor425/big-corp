package com.rrodriguez.glide.bigcorp.converter;

import com.rrodriguez.glide.bigcorp.controller.validation.Expansions;
import com.rrodriguez.glide.bigcorp.model.dao.DepartmentDAO;
import com.rrodriguez.glide.bigcorp.model.dao.EmployeeDAO;
import com.rrodriguez.glide.bigcorp.model.dao.Transformable;
import com.rrodriguez.glide.bigcorp.model.dto.EmployeeDTO;
import com.rrodriguez.glide.bigcorp.service.DepartmentService;
import com.rrodriguez.glide.bigcorp.service.EmployeeService;
import com.rrodriguez.glide.bigcorp.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeConverter {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private OfficeConverter officeConverter;

    @Autowired
    private DepartmentConverter departmentConverter;


    public EmployeeDAO getEmployeeById(Long id, List<Transformation> transformations) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<EmployeeDAO> result = getEmployeesByIds(ids);

        result = transform(transformations, result);

        return result.get(0);
    }

    public List<EmployeeDAO> getEmployeesByIds(List<Long> ids) {
        List<EmployeeDTO> employeesRaw = employeeService.getEmployeesById(ids);

        return employeesRaw.stream()
                .map(EmployeeDAO::new)
                .collect(Collectors.toList());
    }

    private List<EmployeeDAO> transform(List<Transformation> transformations, List<EmployeeDAO> list) {

        List<EmployeeDAO> employeeStage = list;
        List<DepartmentDAO> departmentStage = null;
        List<EmployeeDAO> employeeInterStage = employeeStage;

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
        List<Long> fetchIds = list.stream()
                .map(EmployeeDAO::getManager)
                .filter(Objects::nonNull)
                .map(Transformable::getId)
                .collect(Collectors.toList());

        Map<Long, EmployeeDAO> items = getEmployeesByIds(fetchIds)
                .stream()
                .collect(Collectors.toMap(Transformable::getId, Function.identity()));

        for (EmployeeDAO employeeDAO : list) {
            if (employeeDAO.getManager() != null)
                employeeDAO.setManager(items.get(employeeDAO.getManager().getId()));
        }
        return new ArrayList<>(items.values());
    }


    public List<EmployeeDAO> getEmployees(int limit, int offset) {
        return null;
    }


}
