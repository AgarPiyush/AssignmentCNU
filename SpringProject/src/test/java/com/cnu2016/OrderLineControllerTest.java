package com.cnu2016;

/**
 * Created by Piyush on 7/13/16.
 */
import com.cnu2016.controller.OrderLineController;
import com.cnu2016.model.OrderLine;
import com.cnu2016.model.Orders;
import com.cnu2016.model.Product;
import com.cnu2016.model.Users;
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

public class OrderLineControllerTest {
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
    Product productObj, productObj2;
    OrderLine orderLineObj;
    Users userObj;

    @Before
    public void setUp() {

        orderObj = new Orders();
        productObj = new Product();
        orderLineObj = new OrderLine();
        userObj = new Users();

        orderObj.setStatus("In process");
        orderObj.setDiscontinued(false);
        orderObj.setOrderDate(new Date());

        productObj = new Product();
        productObj.setQuantityInStock(10);

        productObj2 = new Product();
        productObj2.setQuantityInStock(10);


        orderCrud.save(orderObj);
        productCrud.save(productObj);
        productCrud.save(productObj2);

        RestAssured.port = port;
    }

    @Test
    public void addProductToOrder() {
        RestAssured.given().contentType("application/json").
                body("{\"product_id\":\""+productObj.getProductId()+"\", \"qty\":7}").
                when().
                post("api/orders/{id}/orderLineItem",orderObj.getOrderId()).
                then().
                statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void checkoutOrder() {
        String k = "{\"id\":"+orderObj.getOrderId()+"\",\"user_name\":\"Piyush\",\"address\":\"Address\"}";
        System.out.println(k);
        RestAssured.given().contentType("application/json").
                body("{\"product_id\":\""+productObj.getProductId()+"\", \"qty\":7}").
                when().
                post("api/orders/{id}/orderLineItem",orderObj.getOrderId()).
                then().
                statusCode(HttpStatus.SC_CREATED);

        RestAssured.given().contentType("application/json").
                body("{\"id\":"+orderObj.getOrderId()+",\"user_name\":\"Piyush\",\"address\":\"Address\"}").
                when().
                patch("api/orders/{id}",orderObj.getOrderId()).
                then().
                statusCode(HttpStatus.SC_OK).
                body("id", equalTo(orderObj.getOrderId()));

        RestAssured.given().contentType("application/json").
                body("{\"id\":"+orderObj.getOrderId()+",\"address\":\"Address\"}").
                when().
                patch("api/orders/{id}",orderObj.getOrderId()).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);

        RestAssured.given().contentType("application/json").
                body("{\"id\":"+orderObj.getOrderId()+",\"user_name\":\"Piyush\"}").
                when().
                patch("api/orders/{id}",orderObj.getOrderId()).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);

        RestAssured.given().contentType("application/json").
                body("{\"product_id\":\""+productObj2.getProductId()+"\", \"qty\":7}").
                when().
                post("api/orders/{id}/orderLineItem",orderObj.getOrderId()).
                then().
                statusCode(HttpStatus.SC_CREATED);

        RestAssured.given().contentType("application/json").
                body("{\"id\":"+orderObj.getOrderId()+",\"user_name\":\"Piyush\",\"address\":\"Address\"}").
                when().
                patch("api/orders/{id}",orderObj.getOrderId()).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    @Test
    public void heathCheck(){
        RestAssured.when().
                get("api/health/").
                then().
                statusCode(HttpStatus.SC_OK);
    }

}
