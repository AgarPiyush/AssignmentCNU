package com.cnu2016;




/**
 * Created by Piyush on 7/13/16.
 */

import com.cnu2016.controller.ProductController;
import com.cnu2016.model.Product;
import com.cnu2016.repository.ProductRepository;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
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

@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = Application.class)   // 2
@WebAppConfiguration   // 3
@IntegrationTest("server.port:0")   // 4
@TestPropertySource(locations="classpath:test.properties")

public class ProductControllerTest
{
    @Autowired
    ProductRepository prodCrud;

    @Value("${local.server.port}")
        int port;
    Product obj1, obj2;

    @Before
    public void setUp() {
        obj1 = new Product();
        obj2 = new Product();

        obj1.setDiscontinued(false);
        obj1.setProductCode("Code1");
        obj1.setBuyPrice(42.422);
        obj1.setQuantityInStock(100);
        obj2.setDiscontinued(false);
        obj2.setProductCode("Code2");
        obj2.setBuyPrice(43.422);
        obj2.setQuantityInStock(200);

        prodCrud.save(obj1);
        prodCrud.save(obj2);
        RestAssured.port = port;
    }

    @Test
    public void canFetchAProduct() {
        int id = obj1.getProductId();
        String code = obj1.getProductCode();
        int quantity = obj1.getQuantityInStock();
        RestAssured.when().
                get("api/products/{id}", id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("id",Matchers.is(id)).
                body("code",Matchers.is(code)).
                body("qty",Matchers.is(quantity));
    }
    @Test
    public void canFetchAll(){
        RestAssured.when().
                get("api/products/").
                then().
                statusCode(HttpStatus.SC_OK);
    }
    @Test
    public void canPostProduct() {
        RestAssured.given().contentType("application/json").
                body("{\"code\":\"testProduct\"}").
                when().
                post("/api/products").
                then().
                statusCode(HttpStatus.SC_CREATED).
                body("code", equalTo("testProduct"));
    }

    @Test
    public void canDeleteProduct() {
        int id = obj1.getProductId();

        RestAssured.when().
                delete("api/products/{id}",id).
                then().
                statusCode(HttpStatus.SC_OK);

        RestAssured.when().
                get("api/products/{id}", id).
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void canPatchProduct() {
        int id = obj1.getProductId();
        String code = obj1.getProductCode();
        RestAssured.given().contentType("application/json").
                body("{\"code\":\"testProduct\", \"description\":\"newdesciption\"}").
                when().
                patch("/api/products").
                then().
                statusCode(HttpStatus.SC_CREATED).
                body("code", equalTo("testProduct"));


    }

:

}
