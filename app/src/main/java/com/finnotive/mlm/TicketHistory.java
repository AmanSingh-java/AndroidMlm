package com.finnotive.mlm;

public class TicketHistory {
    private String ticket_id;
    private String issue;
    private String description;
    private String status;
    private String remarks;
    private String requestedDate;

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public TicketHistory(String ticket_id, String issue, String description, String status, String remarks, String requestedDate) {
        this.ticket_id = ticket_id;
        this.issue = issue;
        this.description = description;
        this.status = status;
        this.remarks = remarks;
        this.requestedDate = requestedDate;
    }

    public TicketHistory() {
    }

    @Override
    public String toString() {
        return "TicketHistory{" +
                "ticket_id='" + ticket_id + '\'' +
                ", issue='" + issue + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", remarks='" + remarks + '\'' +
                ", requestedDate='" + requestedDate + '\'' +
                '}';
    }
}
