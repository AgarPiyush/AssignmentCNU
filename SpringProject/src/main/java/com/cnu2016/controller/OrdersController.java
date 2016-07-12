package com.cnu2016.controller;

import com.cnu2016.model.Orders;
import com.cnu2016.model.Users;
import com.cnu2016.repository.OrdersRepository;
import com.cnu2016.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ResponseEntity ifNullNotFound()
    {
        Map<String, String> hmap = new HashMap<String, String>();
        hmap.put("detail", "Not found.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hmap);
    }

    @RequestMapping(value = "api/orders", method = RequestMethod.GET)
    public ResponseEntity getAllOrders()
    {
        System.out.println("Order api hit");
        Iterable<Orders> ordersList = orderCrud.findByDiscontinued(false);
        return ResponseEntity.status(HttpStatus.OK).body(ordersList);
    }
    @RequestMapping(value = "api/orderId/{id}", method = RequestMethod.GET)
    public ResponseEntity getUsersOrderByOrderId(@PathVariable("id") int id)
    {
        System.out.println("Inside the api");
        Orders orderObj = orderCrud.findByOrderIdAndDiscontinued(id,false);
        if(orderObj == null)
            return ifNullNotFound();
        //System.out.println(orderObj.getUserObj().getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(orderObj);
    }

    @RequestMapping(value = "api/orders", method = RequestMethod.POST)
    public ResponseEntity createOrder(@RequestBody Orders p)
    {
        System.out.println("Hey");
        Orders obj = new Orders();
        orderCrud.save(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }
    @RequestMapping(value="api/orders/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrder(@PathVariable("id") int id)
    {
        Orders p = orderCrud.findOne(id);
        if(p == null || p.isDiscontinued())
            return ifNullNotFound();

        p.setDiscontinued(true);
        orderCrud.save(p);
        return ResponseEntity.status(HttpStatus.OK).body(p);
    }
}
