package com.backend.dto;

public class ReportDTO {

    private String content;
    private boolean isBadReview;
    private boolean notAppeared;
    private String clientEmail;
    private Integer rentingEntityId;

    public ReportDTO() {}

    public ReportDTO(String content, boolean isBadReview, boolean notAppeared, String clientEmail, Integer rentingEntityId) {
        this.content = content;
        this.isBadReview = isBadReview;
        this.notAppeared = notAppeared;
        this.clientEmail = clientEmail;
        this.rentingEntityId = rentingEntityId;
    }

    public String getContent() { return content; }

    public void setContent(String content) {  this.content = content;  }

    public boolean isBadReview() {  return isBadReview;  }

    public void setBadReview(boolean badReview) {  isBadReview = badReview; }

    public boolean isNotAppeared() {  return notAppeared;  }

    public void setNotAppeared(boolean notAppeared) {   this.notAppeared = notAppeared; }

    public String getClientEmail() {  return clientEmail; }

    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }

    public Integer getRentingEntityId() { return rentingEntityId;  }

    public void setRentingEntityId(Integer rentingEntityId) { this.rentingEntityId = rentingEntityId; }
}
