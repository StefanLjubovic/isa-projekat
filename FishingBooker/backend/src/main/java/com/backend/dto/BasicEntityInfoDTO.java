package com.backend.dto;

import com.backend.model.Address;

public class BasicEntityInfoDTO {
    Integer id;
    String name;
    String description;
    Address address;
    double averageGrade;

    public BasicEntityInfoDTO(Integer id,String name, String description, Address address, double averageGrade) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.averageGrade = averageGrade;
    }
}
