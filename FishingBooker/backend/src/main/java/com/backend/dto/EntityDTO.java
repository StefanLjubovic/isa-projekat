package com.backend.dto;

import com.backend.model.Address;

import java.util.Set;

public class EntityDTO {

    private Integer id;
    private String name;
    private String description;
    private double averageGrade;
    private String image;
    private Address address;
    private String fishingInstructorName;
    private String entityType;

    public EntityDTO() {}

    public EntityDTO(Integer id, String name, String description, double averageGrade, String image, Address address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.averageGrade = averageGrade;
        this.image = image;
        this.address = address;
    }

    public EntityDTO(Integer id, String name, String description, double averageGrade, String image, Address address, String entityType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.averageGrade = averageGrade;
        this.image = image;
        this.address = address;
        this.entityType = entityType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFishingInstructorName() {
        return fishingInstructorName;
    }

    public void setFishingInstructorName(String fishingInstructorName) {
        this.fishingInstructorName = fishingInstructorName;
    }
}
