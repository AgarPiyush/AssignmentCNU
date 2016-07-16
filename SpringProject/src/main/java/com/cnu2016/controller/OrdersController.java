package com.cnu2016.controller;
import com.cnu2016.model.Orders;
import com.cnu2016.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Piyush on 7/8/16.
 */


@RestController
public class OrdersController
{
    @Autowired
    OrdersRepository orderCrud;

    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    private ResponseEntity ifNullNotFound()
    {
        logger.error("Not Found error");
        Map<String, String> hmap = new HashMap<String, String>();
        hmap.put("detail", "Not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hmap);
    }

    @RequestMapping(value = "api/orders", method = RequestMethod.GET)
    public ResponseEntity getAllOrders()
    {
        logger.info("Orders api hit get all orders");
        Iterable<Orders> ordersList = orderCrud.findByDiscontinued(false);
        if(ordersList == null)
            return ifNullNotFound();
        return ResponseEntity.status(HttpStatus.OK).body(ordersList);
    }
    @RequestMapping(value = "api/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity getUsersOrderByOrderId(@PathVariable("id") int id)
    {
        logger.info("get order by id "+id);
        Orders orderObj = orderCrud.findByOrderIdAndDiscontinued(id,false);
        if(orderObj == null)
            return ifNullNotFound();
        return ResponseEntity.status(HttpStatus.OK).body(orderObj);
    }

    @RequestMapping(value = "api/orders", method = RequestMethod.POST)
    public ResponseEntity createOrder(@RequestBody Orders p)
    {
        logger.info("Post order. Creates order");
        Orders obj = new Orders();
        orderCrud.save(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }
    @RequestMapping(value="api/orders/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrder(@PathVariable("id") int id)
    {
        logger.info("Delete order");
        Orders p = orderCrud.findOne(id);
        if(p == null || p.isDiscontinued())
            return ifNullNotFound();
        p.setDiscontinued(true);
        orderCrud.save(p);
        return ResponseEntity.status(HttpStatus.OK).body(p);
    }
}
