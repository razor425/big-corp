package com.rrodriguez.glide.bigcorp.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rrodriguez.glide.bigcorp.model.dto.DepartmentDTO;
import com.rrodriguez.glide.bigcorp.model.serializer.DepartmentSerializer;

public class DepartmentDAO {

    private Long id;
    private String name;

    @JsonSerialize(using = DepartmentSerializer.class)
    private DepartmentDAO superdepartment;

    public DepartmentDAO(DepartmentDTO base) {
        this.id = base.getId();
        this.name = base.getName();
        if (base.getSuperdepartment() != null)
            this.superdepartment = new DepartmentDAO(base.getSuperdepartment());
    }

    public DepartmentDAO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentDAO getSuperdepartment() {
        return superdepartment;
    }

    public void setSuperdepartment(DepartmentDAO superdepartment) {
        this.superdepartment = superdepartment;
    }

    @JsonIgnore
    public boolean isPrimitive() {
        return this.name == null;
    }
}
