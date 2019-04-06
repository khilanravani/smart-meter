package com.example.projectmeter;

public class Bill {
    private String time;
    private Double cost;
    private Boolean is_paid;

    public Bill(String time, Double cost, Boolean is_paid) {
        this.time = time;
        this.cost = cost;
        this.is_paid = is_paid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Boolean getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(Boolean is_paid) {
        this.is_paid = is_paid;
    }
}
