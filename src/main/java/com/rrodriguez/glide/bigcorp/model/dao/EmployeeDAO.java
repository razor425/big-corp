package com.rrodriguez.glide.bigcorp.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rrodriguez.glide.bigcorp.model.dto.EmployeeDTO;
import com.rrodriguez.glide.bigcorp.model.serializer.DepartmentSerializer;
import com.rrodriguez.glide.bigcorp.model.serializer.EmployeeSerializer;
import com.rrodriguez.glide.bigcorp.model.serializer.OfficeSerializer;

public class EmployeeDAO {

    private Long id;
    private String first;
    private String last;

    @JsonSerialize(using = EmployeeSerializer.class)
    private EmployeeDAO manager;
    @JsonSerialize(using = DepartmentSerializer.class)
    private DepartmentDAO department;
    @JsonSerialize(using = OfficeSerializer.class)
    private OfficeDAO office;

    public EmployeeDAO(EmployeeDTO base) {
        this.id = base.getId();
        this.first = base.getFirst();
        this.last = base.getLast();

        if (base.getDepartment() != null)
            this.department = new DepartmentDAO(base.getDepartment());
        if (base.getManager() != null)
            this.manager = new EmployeeDAO(base.getManager());
        if (base.getOffice() != null)
            this.office = new OfficeDAO(base.getOffice());
    }

    public EmployeeDAO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public EmployeeDAO getManager() {
        return manager;
    }

    public void setManager(EmployeeDAO manager) {
        this.manager = manager;
    }

    public DepartmentDAO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDAO department) {
        this.department = department;
    }

    public OfficeDAO getOffice() {
        return office;
    }

    public void setOffice(OfficeDAO office) {
        this.office = office;
    }

    @JsonIgnore
    public boolean isPrimitive() {
        return this.first == null;
    }
}
