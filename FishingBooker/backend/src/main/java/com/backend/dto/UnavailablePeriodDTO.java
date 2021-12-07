package com.backend.dto;

import com.backend.model.UnavailablePeriod;

import java.util.Date;

public class UnavailablePeriodDTO {

    private Integer id;
    private Date fromDateTime;
    private Date toDateTime;
    private String message;

    public UnavailablePeriodDTO() {}

    public UnavailablePeriodDTO(Integer id, Date fromDateTime, Date toDateTime, String message) {
        this.id = id;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.message = message;
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
}
