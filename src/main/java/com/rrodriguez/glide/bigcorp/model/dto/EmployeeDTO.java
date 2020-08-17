package com.rrodriguez.glide.bigcorp.model.dto;

public class EmployeeDTO {

    private String first;
    private String last;
    private Long id;
    private Long manager;
    private Long department;
    private Long office;

    public EmployeeDTO() {

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

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    public Long getOffice() {
        return office;
    }

    public void setOffice(Long office) {
        this.office = office;
    }
}
