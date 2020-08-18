package com.rrodriguez.glide.bigcorp.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rrodriguez.glide.bigcorp.model.dto.OfficeDTO;

public class OfficeDAO {

    private Long id;
    private String city;
    private String country;
    private String address;

    public OfficeDAO(Long id) {
        this.id = id;
    }

    public OfficeDAO(OfficeDTO base) {
        this.id = base.getId();
        this.city = base.getCity();
        this.country = base.getCountry();
        this.address = base.getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonIgnore
    public boolean isPrimitive() {
        return this.city == null;
    }
}
