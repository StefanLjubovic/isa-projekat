package com.backend.dto;

import java.util.Date;

public class ReservationIncomeDTO {

    private String entityName;
    private String clientEmail;
    private double income;
    private Date dateFrom;
    private Date dateTo;

    public ReservationIncomeDTO() {}

    public ReservationIncomeDTO(String entityName, String clientEmail, Date dateFrom, Date dateTo) {
        this.entityName = entityName;
        this.clientEmail = clientEmail;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income, double percent) {
        this.income = income * percent/100;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

}
