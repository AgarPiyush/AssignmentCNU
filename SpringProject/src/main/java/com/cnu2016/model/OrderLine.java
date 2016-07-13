package com.cnu2016.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.persistence.*;

/**
 * Created by Piyush on 7/9/16.
 */
@Entity
@Table(name = "OrderLine_copy")
public class OrderLine {
    @EmbeddedId
    OrderLineKey id;

    private int quantityOrdered;
    private double priceEach;
    public OrderLine()
    {

    }
    public OrderLineKey getId() {
        return id;
    }

    public void setId(OrderLineKey id) {
        this.id = id;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public double getPriceEach() {
        return priceEach;
    }

    public void setPriceEach(double priceEach) {
        this.priceEach = priceEach;
    }
}
