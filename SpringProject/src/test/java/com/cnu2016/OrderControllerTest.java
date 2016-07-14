package com.cnu2016;

/**
 * Created by Piyush on 7/13/16.
 */

import com.cnu2016.controller.OrderLineController;
import com.cnu2016.model.Orders;
import com.cnu2016.model.Product;
import com.cnu2016.repository.FeedbackRepository;
import com.cnu2016.repository.OrdersRepository;
import com.cnu2016.repository.ProductRepository;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = Application.class)   // 2
@WebAppConfiguration   // 3
@IntegrationTest("server.port:0")   // 4
@TestPropertySource(locations="classpath:test.properties")


public class OrderControllerTest {
    @Autowired
    OrdersRepository orderCrud;
    @Autowired
    OrderLineController orderLineCrud;
    @Autowired
    ProductRepository productCrud;
    @Autowired
    FeedbackRepository feedbackCrud;

    @Value("${local.server.port}")
        int port;
    Orders orderObj;

    @Before
    public void setUp() {
        orderObj = new Orders();
        orderObj.setStatus("In process");
        orderObj.setDiscontinued(false);
        orderObj.setOrderDate(new Date());
        orderCrud.save(orderObj);
        RestAssured.port = port;
    }
    @Test
    public void canFetchAllOrders(){
        RestAssured.when().
                get("api/orders/").
                then().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void canFetchOrder() {
        int id = orderObj.getOrderId();
        RestAssured.when().
                get("api/orders/{id}", id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("id",equalTo(id)).
                body("status", equalTo(orderObj.getStatus())).
                body("discontinued", equalTo(orderObj.isDiscontinued()));

        RestAssured.when().
                get("api/orders/{id}", 98898598).
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void canPostOrder() {
        RestAssured.given().contentType("application/json").
                body("{}").
                when().
                post("/api/orders").
                then().
                statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void canDeleteOrder() {
        int id = orderObj.getOrderId();

        RestAssured.when().
                delete("api/orders/{id}",id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("discontinued",equalTo(true));

        RestAssured.when().
                get("api/orders/{id}", id).
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);

        RestAssured.when().
                delete("api/orders/{id}",id).
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
