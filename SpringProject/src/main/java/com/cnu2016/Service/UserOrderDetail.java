package com.cnu2016.Service;

import com.cnu2016.model.Orders;
import com.cnu2016.model.Users;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Piyush on 7/11/16.
 */
public class UserOrderDetail {

    private String address;
    private String userName;
    private String pincode;
    private String status;

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPincode() {
        return pincode;
    }

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserNname(String userName) {
        this.userName = userName;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
