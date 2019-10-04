package com.myRetail.demo.restservice.controller;

import com.myRetail.demo.restservice.domain.Price;
import com.myRetail.demo.restservice.domain.Product;
import com.myRetail.demo.restservice.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
public class MyRetailProductControllerUTest {


    @LocalServerPort
    int port;

    @Autowired
    private ProductRepository productRepository;

    public static final String API_URL = "/myRetail/api";

    @Before
    public void setup(){
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
        
        Product product = new Product();
        product.setId(13860428);
        product.setName("The Big Lebowski (Blu-ray)");
        product.setPrice(new Price(13.49,"USD",null));

        productRepository.addNewProduct(product);
    }

    @Test
    public void test_getProductById(){
        Response detail = RestAssured.when().get(API_URL+ "/products/13860428")
                .then().statusCode(HttpStatus.SC_OK)
                .log().body()
                .extract().response();

        assertThat(detail.body().toString().contains("The Big Lebowski (Blu-ray)"));
    }

    @Test
    public void test_updateProductById(){
        Product product = new Product();
        product.setId(13860428);
        product.setName("The Big Lebowski (Blu-ray)");
        product.setPrice(new Price(1.49,"USD",null));

        Response detail = RestAssured.given()
                .contentType("application/json")
                .body(product)
                .when().put(API_URL+ "/products/13860428")
                .then().statusCode(HttpStatus.SC_OK)
                .log().body()
                .extract().response();

        assertThat(detail.body().toString().contains("The Big Lebowski (Blu-ray)"));
        assertThat(detail.body().toString().contains("1.49"));
    }

    @Test
    public void test_addNewProduct(){
        Product product = new Product();
        product.setId(13860429);
        product.setName("Test Product");
        product.setPrice(new Price(99.99,"USD",null));

        Response detail = RestAssured.given()
                .contentType("application/json")
                .body(product)
                .when().post(API_URL+ "/products/addNew")
                .then().statusCode(HttpStatus.SC_OK)
                .log().body()
                .extract().response();

        assertThat(detail.body().toString().contains("Test Product"));
        assertThat(detail.body().toString().contains("1.49"));
    }
}
