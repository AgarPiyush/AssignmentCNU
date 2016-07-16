package com.cnu2016;

/**
 * Created by Piyush on 7/14/16.
 */
import com.cnu2016.controller.OrderLineController;
import com.cnu2016.model.*;
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


public class FeedBackControllerTest {

    @Value("${local.server.port}")
        int port;
    Feedback feedbackObj;

    @Autowired
    FeedbackRepository feedbackCrud;
    @Before
    public void setUp() {
        feedbackObj = new Feedback();
        feedbackObj.setUserName("Piyush");
        feedbackObj.setDescription("Desc");
        feedbackObj.setEmail("com@com.com");
        feedbackObj.setTimeStamp(new Date());
        feedbackObj.setOrderId(10);
        feedbackCrud.save(feedbackObj);
        RestAssured.port = port;
    }
    @Test
    public void canFetchAllFeedback(){
        RestAssured.when().
                get("api/contact/").
                then().
                statusCode(HttpStatus.SC_OK);
    }
    @Test
    public void canPostFeedback() {
        System.out.println(feedbackObj.getId());
        int id = feedbackObj.getId();
        RestAssured.given().contentType("application/json").
                body("{\"user_name:\":\""+feedbackObj.getUserName()+"\"}").
                when().
                post("/api/contact/").
                then().
                statusCode(HttpStatus.SC_CREATED);
    }
}
