package com.rrodriguez.glide.bigcorp.model.dto;

public class CustomEmployeeDTO {

    private String first;
    private String last;
    private Long id;
    private CustomEmployeeDTO manager;
    private CustomDepartmentDTO department;
    private CustomOfficeDTO office;

    public CustomEmployeeDTO(Long id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public CustomEmployeeDTO(Long id) {
        this.id=id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomEmployeeDTO getManager() {
        return manager;
    }

    public void setManager(CustomEmployeeDTO manager) {
        this.manager = manager;
    }

    public CustomDepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(CustomDepartmentDTO department) {
        this.department = department;
    }

    public CustomOfficeDTO getOffice() {
        return office;
    }

    public void setOffice(CustomOfficeDTO office) {
        this.office = office;
    }
}
