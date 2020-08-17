package com.rrodriguez.glide.bigcorp.model.dto;

public class DepartmentDTO {

    private Long id;
    private String name;
    private Long superdepartment;

    public DepartmentDTO() {

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

    public Long getSuperdepartment() {
        return superdepartment;
    }

    public void setSuperdepartment(Long superdepartment) {
        this.superdepartment = superdepartment;
    }
}
