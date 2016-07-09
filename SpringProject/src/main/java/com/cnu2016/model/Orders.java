package com.cnu2016.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Piyush on 7/8/16.
 */
@Entity
@Table(name="Orders")
public class Orders
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private Date orderDate;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "userId")
    private Users userObj;

 /*   @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "orderItem")
    private OrderLine orderLineObj;  */

    public Orders()
    {
        this.orderDate = new Date();
        this.status = "In Process";
    }
    /*public Orders(Date orderDate, Users userId, String status)
    {
        this.orderDate = orderDate;
        this.userId = userId;
        this.status = status;
    }
    */
    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Users getUserObj() {
        return userObj;
    }

    public void setUserObj(Users userObj) {
        this.userObj = userObj;
    }
}
