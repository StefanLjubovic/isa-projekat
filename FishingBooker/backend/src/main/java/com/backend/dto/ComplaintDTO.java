package com.backend.dto;

import com.backend.model.Client;
import com.backend.model.RentingEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ComplaintDTO {

    private Integer id;
    private String content;
    private Integer entityId;

    private String clientEmail;
    private String clientFullName;
    private String advertiserFullName;
    private String entityName;
    private String type;

    public ComplaintDTO() {}

    public ComplaintDTO(String content, Integer entityId) {
        this.content = content;
        this.entityId = entityId;
    }

    public ComplaintDTO(Integer id, String content, Integer entityId, String clientEmail, String clientFullName, String advertiserFullName, String entityName, String type) {
        this.id = id;
        this.content = content;
        this.entityId = entityId;
        this.clientEmail = clientEmail;
        this.clientFullName = clientFullName;
        this.advertiserFullName = advertiserFullName;
        this.entityName = entityName;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getAdvertiserFullName() {
        return advertiserFullName;
    }

    public void setAdvertiserFullName(String advertiserFullName) {
        this.advertiserFullName = advertiserFullName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
