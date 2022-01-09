package com.backend.dto;

import com.backend.model.Client;
import com.backend.model.RentingEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ReservationDTO {

    private Integer id;
    private Date dateTime;
    private int durationInHours;
    private int maxPersons;
    private Set<String> additionalServices = new HashSet<String>();
    private double price;
    private Boolean isCanceled = false;
    private Integer entityId;
    private String entityName;
    private String clientName;
    private String clientEmail;

    public ReservationDTO() {}

    public ReservationDTO(Integer id, Date dateTime, int durationInHours, int maxPersons, double price, Boolean isCanceled, Integer entityId, String entityName) {
        this.id = id;
        this.dateTime = dateTime;
        this.durationInHours = durationInHours;
        this.maxPersons = maxPersons;
        this.price = price;
        this.isCanceled = isCanceled;
        this.entityId = entityId;
        this.entityName = entityName;
    }

    public ReservationDTO(Integer id, Date dateTime, int durationInHours, int maxPersons, double price, Boolean isCanceled, Integer entityId, String entityName, String clientName, String clientEmail) {
        this.id = id;
        this.dateTime = dateTime;
        this.durationInHours = durationInHours;
        this.maxPersons = maxPersons;
        this.price = price;
        this.isCanceled = isCanceled;
        this.entityId = entityId;
        this.entityName = entityName;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
    }

    public ReservationDTO(Date dateTime, int durationInHours, int maxPersons, Set<String> additionalServices, double price, Boolean isCanceled, Integer entityId, String entityName) {
        this.dateTime = dateTime;
        this.durationInHours = durationInHours;
        this.maxPersons = maxPersons;
        this.additionalServices = additionalServices;
        this.price = price;
        this.isCanceled = isCanceled;
        this.entityId = entityId;
        this.entityName = entityName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    public int getMaxPersons() {
        return maxPersons;
    }

    public void setMaxPersons(int maxPersons) {
        this.maxPersons = maxPersons;
    }

    public Set<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
}
