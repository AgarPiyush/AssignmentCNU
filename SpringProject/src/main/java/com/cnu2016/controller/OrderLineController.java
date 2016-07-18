package com.cnu2016.controller;

import com.cnu2016.ProductSerializer;
import com.cnu2016.Service.UserOrderDetail;
import com.cnu2016.model.*;
import com.cnu2016.repository.OrderLineReporsitory;
import com.cnu2016.repository.OrdersRepository;
import com.cnu2016.repository.ProductRepository;
import com.cnu2016.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Piyush on 7/9/16.
 */
@RestController
public class OrderLineController
{
    @Autowired
    OrderLineReporsitory orderLineCrud;

    @Autowired
    ProductRepository productCrud;

    @Autowired
    OrdersRepository orderCrud;

    @Autowired
    UsersRepository userCrud;

    private static final Logger logger = LoggerFactory.getLogger(OrderLineController.class);

    /**
     * @param orderId
     * @return valid
     * Checks if the order is valid or not. Not valid if any
     * product exceeds the stock quantity
     */
    private int isOrderValid(int orderId)
    {
        logger.info("Check for order validity"+orderId);
        int valid = 1;
        Iterable<OrderLine> all = orderLineCrud.findAll();
        for(OrderLine it:all)
        {
            OrderLineKey orderLineKey = it.getId();
            Orders orderObj = orderLineKey.getOrderLineOrdersObj();
            Product productObj = orderLineKey.getOrderLineProductObj();

            if(orderObj.getOrderId() == orderId)
            {
                Product product = productCrud.findByProductId(productObj.getProductId());

                if(it.getQuantityOrdered() > product.getQuantityInStock())
                {
                    valid = 0;
                }
            }
        }
        return valid;
    }
    // Add products to existing cart
    @RequestMapping(value = "api/orders/{id}/orderLineItem", method = RequestMethod.POST)
    public ResponseEntity addProductInOrder(@RequestBody ProductSerializer p, @PathVariable("id") int id)
    {
        logger.info("Add product to card orderId "+id+" productid "+p.getProductId());
        Product productObj = productCrud.findByProductIdAndDiscontinued(p.getProductId(),false);
        Orders ordersObj = orderCrud.findByOrderIdAndDiscontinued(id,false);

        if(productObj == null || ordersObj == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        OrderLine orderLineObj = new OrderLine();
        OrderLineKey orderLineKeyObj = new OrderLineKey();

        orderLineKeyObj.setOrderLineOrdersObj(ordersObj);
        orderLineKeyObj.setOrderLineProductObj(productObj);

        orderLineObj.setId(orderLineKeyObj);
        orderLineObj.setPriceEach(productObj.getBuyPrice());
        orderLineObj.setQuantityOrdered(p.getQuantity());

        orderLineCrud.save(orderLineObj);
        if(productObj.getQuantityInStock() - p.getQuantity() < 0) {
            logger.info("Quantity added is greater than stock");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(orderLineObj);

    }

    /**
     *  Checkout order. Creates new user. Check for validity of order
     *  Updates inventory if order valid
     */
    @RequestMapping(value = "api/orders/{id}", method = RequestMethod.PATCH)
    public ResponseEntity checkout(@RequestBody UserOrderDetail p, @PathVariable("id") int id)
    {

        logger.info("Check out item orderId "+id);
        if(p.getUserName() == null || p.getAddress() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

        Orders ordersObj = orderCrud.findByOrderId(id);
        if(ordersObj == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");

        Users userObj = userCrud.findByCustomerName(p.getUserName());
        if(userObj == null) //creating new user
        {
            logger.info("Creating new user");
            userObj = new Users();
            userObj.setCustomerName(p.getUserName());
            userObj.setAddressLine1(p.getAddress());
            userCrud.save(userObj);
        }

        ordersObj.setUserObj(userObj);
        ordersObj.setStatus(p.getStatus());
        orderCrud.save(ordersObj);

        int valid = isOrderValid(id);
        if(valid == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");


        double totalPrice = 0;
        Iterable<OrderLine> all = orderLineCrud.findAll();

        for(OrderLine it:all)
        {
            OrderLineKey orderLineKey = it.getId();
            Orders orderObj = orderLineKey.getOrderLineOrdersObj();
            Product productObj = orderLineKey.getOrderLineProductObj();
            if(orderObj.getOrderId() == id)
            {
                Product product = productCrud.findByProductId(productObj.getProductId());
                product.setQuantityInStock(product.getQuantityInStock() - it.getQuantityOrdered());
                totalPrice = totalPrice + product.getBuyPrice()*it.getQuantityOrdered();
                productCrud.save(product);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ordersObj);
    }

    @RequestMapping(value = "api/health", method = RequestMethod.GET)
    public ResponseEntity healthCheck()
    {
        logger.info("Health check");
        return ResponseEntity.status(HttpStatus.OK).body("");
    }


}
