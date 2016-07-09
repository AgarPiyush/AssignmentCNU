package com.cnu2016.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Piyush on 7/9/16.
 */

@Embeddable
public class OrderLineKey implements Serializable {


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "productId")
    private Product orderLineProductObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "orderId")
    private Orders orderLineOrdersObj;


    OrderLineKey(){
    }

    public Product getOrderLineProductObj() {
        return orderLineProductObj;
    }

    public void setOrderLineProductObj(Product orderLineProductObj) {
        this.orderLineProductObj = orderLineProductObj;
    }

    public Orders getOrderLineOrdersObj() {
        return orderLineOrdersObj;
    }

    public void setOrderLineOrdersObj(Orders orderLineOrdersObj) {
        this.orderLineOrdersObj = orderLineOrdersObj;
    }
}
