package com.backend.dto;

public class RejectRegistrationDTO {

    private Integer id;
    private String rejectionReason;

    public RejectRegistrationDTO() { }

    public RejectRegistrationDTO(Integer id, String rejectionReason) {
        this.id = id;
        this.rejectionReason = rejectionReason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
