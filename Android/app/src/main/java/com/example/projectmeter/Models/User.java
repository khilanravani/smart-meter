package com.example.projectmeter.Models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    @SerializedName("user_name")
    String userName;
    @SerializedName("email_id")
    String emailId;
    @SerializedName("phone_number")
    String phoneNumber;
    @SerializedName("address")
    String address;
    @SerializedName("meter_id")
    int userId;
    @SerializedName("pin")
    int pin;

    public User(JSONObject userJson) throws JSONException {
        this.userId = userJson.getInt("id");
        this.userName = userJson.getString("name");
        this.emailId = userJson.getString("emailId");
        this.phoneNumber = userJson.getString("phoneNumber");
        this.address = userJson.getString("address");
        this.pin = userJson.getInt("pin");
    }

    public User(String userName, int userId, String emailId, String phoneNumber, String address, int pin) {
        this.userName = userName;
        this.userId = userId;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.pin = pin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
