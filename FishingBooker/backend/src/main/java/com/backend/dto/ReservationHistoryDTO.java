package com.backend.dto;

import com.backend.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class ReservationHistoryDTO {
    private Integer id;
    private Date dateTime;
    private int durationInHours;
    private double price;
    private Integer entityId;
    private String entityName;
    private Client client;
    @JsonIgnore
    private String clientEmail;

    public ReservationHistoryDTO() {}

    public ReservationHistoryDTO(Integer id, Date dateTime, int durationInHours, double price, Integer entityId, String entityName, Client client) {
        this.id = id;
        this.dateTime = dateTime;
        this.durationInHours = durationInHours;
        this.price = price;
        this.entityId = entityId;
        this.entityName = entityName;
        this.client = client;
    }

    public ReservationHistoryDTO(Integer id, Date dateTime, int durationInHours, double price, Integer entityId, String entityName, String email) {
        this.id = id;
        this.dateTime = dateTime;
        this.durationInHours = durationInHours;
        this.price = price;
        this.entityId = entityId;
        this.entityName = entityName;
        this.clientEmail = email;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Client getClient() { return client; }

    public void setClient(Client client) {  this.client = client; }

    public String getClientEmail() { return clientEmail; }

    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
}
