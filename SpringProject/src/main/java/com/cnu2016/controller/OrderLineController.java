package com.cnu2016.controller;

import com.cnu2016.model.OrderLine;
import com.cnu2016.model.Orders;
import com.cnu2016.repository.OrderLineReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Piyush on 7/9/16.
 */
@RestController
public class OrderLineController
{
    @Autowired
    OrderLineReporsitory orderLineCrud;

    @RequestMapping(value = "api/orderLine", method = RequestMethod.GET)
    public ResponseEntity createOrders()
    {
        System.out.println("Order api hit");

        for(OrderLine orderLine: orderLineCrud.findAll())
        {
            System.out.println(orderLine.getId());
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderLineCrud.findAll());
    }

}
