package com.rrodriguez.glide.bigcorp.model.dto;

public class CustomDepartmentDTO {

    private Long id;
    private String name;
    private CustomDepartmentDTO superdepartment;

    public CustomDepartmentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CustomDepartmentDTO(Long id){
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

    public CustomDepartmentDTO getSuperdepartment() {
        return superdepartment;
    }

    public void setSuperdepartment(CustomDepartmentDTO superdepartment) {
        this.superdepartment = superdepartment;
    }
}
