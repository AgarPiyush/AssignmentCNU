package com.cnu2016.controller;

import com.cnu2016.ProductSerializer;
import com.cnu2016.Service.UserOrderDetail;
import com.cnu2016.model.*;
import com.cnu2016.repository.OrderLineReporsitory;
import com.cnu2016.repository.OrdersRepository;
import com.cnu2016.repository.ProductRepository;
import com.cnu2016.repository.UsersRepository;
import org.apache.catalina.connector.Response;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

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

    private int isorderValid(int orderId)
    {
        System.out.println("Check orderValid");
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
        System.out.println("Check orderValid done");

        return valid;
    }
    private ResponseEntity ifNullNotFound()
    {
        Map<String, String> hmap = new HashMap<String, String>();
        hmap.put("detail", "Not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hmap);
    }
    @RequestMapping(value = "api/orders/{id}/orderLineItem", method = RequestMethod.POST)
    public ResponseEntity addProduct(@RequestBody ProductSerializer p, @PathVariable("id") int id)
    {
        if(p == null)
        {
            System.out.println("Request body empty");
            return ifNullNotFound();
        }
        System.out.println("Inside add product");
        System.out.println("Product id "+p.getProductId()+" orderId "+id);
        Product productObj = productCrud.findByProductId(p.getProductId());
        Orders ordersObj = orderCrud.findByOrderId(id);

        if(productObj == null || ordersObj == null) {
            System.out.println("Product and Order not foundß");

            return ifNullNotFound();
        }
        if(productObj.getQuantityInStock() - p.getQuantity() < 0) {
            System.out.println("Quantity exceeded");

            return ifNullNotFound();
        }

        OrderLine orderLineObj = new OrderLine();
        OrderLineKey orderLineKeyObj = new OrderLineKey();

        orderLineKeyObj.setOrderLineOrdersObj(ordersObj);
        orderLineKeyObj.setOrderLineProductObj(productObj);

        orderLineObj.setId(orderLineKeyObj);
        orderLineObj.setPriceEach(productObj.getBuyPrice());
        orderLineObj.setQuantityOrdered(p.getQuantity());

        orderLineCrud.save(orderLineObj);
        return ResponseEntity.status(HttpStatus.OK).body(orderLineObj);

    }
    @RequestMapping(value = "api/orders/{id}", method = RequestMethod.PATCH)
    public ResponseEntity checkout(@RequestBody UserOrderDetail p, @PathVariable("id") int id)
    {
        if(p == null)
            return ifNullNotFound();

        Orders ordersObj = orderCrud.findByOrderId(id);
        if(ordersObj == null)
            ifNullNotFound();

        Users userObj = userCrud.findByCustomerName(p.getUserName());
        if(userObj == null) //creating new user
        {
            userObj = new Users();
            userObj.setCustomerName(p.getUserName());
            userObj.setAddressLine1(p.getAddress());
            userCrud.save(userObj);
        }

        ordersObj.setUserObj(userObj);
        ordersObj.setStatus(p.getStatus());
        orderCrud.save(ordersObj);
        System.out.println("Before Validity ");

        int valid = isorderValid(id);
        System.out.println("Valid "+valid);
        if(valid == 0)
        {
            System.out.println("OrderNotValid");
            return ifNullNotFound();
        }

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
        return ResponseEntity.status(HttpStatus.OK).body(totalPrice);
    }

    @RequestMapping(value = "api/health", method = RequestMethod.GET)
    public ResponseEntity healthCheck()
    {
        System.out.println("Hey");
        return ResponseEntity.status(HttpStatus.OK).body("");
    }


}
