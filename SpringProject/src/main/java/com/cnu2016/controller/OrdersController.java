package com.cnu2016.controller;

import com.cnu2016.model.Orders;
import com.cnu2016.model.Users;
import com.cnu2016.repository.OrdersRepository;
import com.cnu2016.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Piyush on 7/8/16.
 */
@RestController
public class OrdersController
{
    @Autowired
    OrdersRepository orderCrud;
    @Autowired
    UsersRepository usersCrud;

    @RequestMapping(value = "api/orders", method = RequestMethod.GET)
    public ResponseEntity createOrders()
    {
        System.out.println("Order api hit");
        Iterable<Orders> ordersList = orderCrud.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ordersList);
    }
    @RequestMapping(value = "api/ordersId/{id}", method = RequestMethod.GET)
    public ResponseEntity getUsersByOrderId(@PathVariable("id") int id)
    {
        System.out.println("Inside the api");
        Orders orderObj = orderCrud.findOne(id);
        System.out.println(orderObj.getUserObj().getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(orderObj.getUserObj());
    }

    @RequestMapping(value = "api/order", method = RequestMethod.POST)
    public ResponseEntity createOrder(@RequestBody Orders p)
    {
        Orders obj = new Orders();
        orderCrud.save(obj);
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }
}
