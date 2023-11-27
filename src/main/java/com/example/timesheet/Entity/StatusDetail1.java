package com.example.timesheet.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "statusdetail1")
public class StatusDetail1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Status_Id")
    private int statusId;

    @ManyToOne
    @JoinColumn(name = "User_ID", referencedColumnName = "User_ID")
    private Users user;

    @Column(name = "status")
    private String status;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public StatusDetail1() {
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

