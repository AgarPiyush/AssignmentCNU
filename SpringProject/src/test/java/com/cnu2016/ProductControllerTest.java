package com.cnu2016;




/**
 * Created by Piyush on 7/13/16.
 */

import com.cnu2016.controller.ProductController;
import com.cnu2016.model.Category;
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
    Category categoryObj;

    @Before
    public void setUp() {
        obj1 = new Product();
        obj2 = new Product();

        obj1.setDiscontinued(false);
        obj1.setProductCode("code1");
        obj1.setBuyPrice(42.422);
        obj1.setProductDescription("description1");
        obj1.setQuantityInStock(100);
        prodCrud.save(obj1);
        // Category Object was not required
        categoryObj = new Category();
        categoryObj.setCategoryId(10);
        categoryObj.setCategoryDescription("Des");
        String categoryDescription = categoryObj.getCategoryDescription();
        int categoryId = categoryObj.getCategoryId();
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

        RestAssured.when().
                get("api/products/{id}", 988988).
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);

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
                delete("api/products/{id}", id).
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void canPatchProduct() {
        int id = obj1.getProductId();
        String obj1ProductCode = obj1.getProductCode();
        String obj1ProductDescription = obj1.getProductDescription();
        System.out.println("{\"code\":\""+obj1ProductCode+"\", \"description\":\"newDescription\"}");
        RestAssured.given().contentType("application/json").
                body("{\"code\":\""+obj1ProductCode+"\", \"description\":\"newDescription\"}").
                when().
                patch("/api/products/{id}",id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("code", equalTo(obj1ProductCode)).
                body("description",equalTo("newDescription")).
                body("id", equalTo(id));


        RestAssured.given().contentType("application/json").
                body("{\"code\":\"newcode\", \"description\":\"newDescription\"}").
                when().
                patch("/api/products/{id}",id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("code", equalTo("newcode")).
                body("description",equalTo("newDescription")).
                body("id", equalTo(id));


        RestAssured.given().contentType("application/json").
                body("{\"code\":\"newcode\"}").
                when().
                patch("/api/products/{id}",id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("code", equalTo("newcode")).
                body("description",equalTo(prodCrud.findByProductId(id).getProductDescription())).
                body("id", equalTo(id));

    }
    @Test
    public void canPutProduct() {
        System.out.println("Inside patch");
        int id = obj1.getProductId();
        String obj1ProductCode = obj1.getProductCode();
        String obj1ProductDescription = obj1.getProductDescription();
        //System.out.println("{\"code\":\""+obj1ProductCode+"\", \"description\":\"newDescription\"}");

        RestAssured.given().contentType("application/json").
                body("{}").
                when().
                put("/api/products/{id}",id).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);

        RestAssured.given().contentType("application/json").
                body("{\"description\":\"newDescription\"}").
                when().
                put("/api/products/{id}",id).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);

        RestAssured.given().contentType("application/json").
                body("{\"code\":\""+obj1ProductCode+"\", \"description\":\"newDescription\"}").
                when().
                put("/api/products/{id}",id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("code",equalTo(obj1ProductCode)).
                body("description", equalTo(prodCrud.findByProductId(id).getProductDescription())).
                body("id",equalTo(id));

        RestAssured.given().contentType("application/json").
                body("{\"code\":\""+obj1ProductCode+"\"}").
                when().
                put("/api/products/{id}",id).
                then().
                statusCode(HttpStatus.SC_OK).
                body("code",equalTo(obj1ProductCode)).
                body("description", equalTo(null)).
                body("id",equalTo(id));
    }

}
