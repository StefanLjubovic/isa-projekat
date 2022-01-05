package com.backend.dto;

import java.util.Date;

public class UnavailablePeriodDTO {

    private Integer id;
    private Date fromDateTime;
    private Date toDateTime;
    private String message;
    private String entityName;

    public UnavailablePeriodDTO() {}

    public UnavailablePeriodDTO(Integer id, Date fromDateTime, Date toDateTime, String message) {
        this.id = id;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.message = message;
    }

    public UnavailablePeriodDTO(Date fromDateTime, Date toDateTime, String name){
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.entityName = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Date fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Date getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Date toDateTime) {
        this.toDateTime = toDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntityName() {   return entityName;  }

    public void setEntityName(String entityName) {  this.entityName = entityName; }
}
