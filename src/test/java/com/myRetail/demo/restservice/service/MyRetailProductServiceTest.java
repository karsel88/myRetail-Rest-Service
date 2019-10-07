package com.myRetail.demo.restservice.service;

import com.myRetail.demo.restservice.domain.Price;
import com.myRetail.demo.restservice.domain.Product;
import com.myRetail.demo.restservice.exception.ProductException;
import com.myRetail.demo.restservice.exception.ProductNotFoundException;
import com.myRetail.demo.restservice.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyRetailProductServiceTest {

    private double CHECK_PRICE = 1.49;
    private String PRODUCT_NAME = "The Big Lebowski (Blu-ray)";

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void before() {
        Product product = new Product();
        product.setId(13860428);
        product.setName("The Big Lebowski (Blu-ray)");
        product.setPrice(new Price(13.49,"USD",null));
        productRepository.addNewProduct(product);
    }

    @Test
    public void test_addNewProduct() {
        Product product = new Product();
        product.setId(13860429);
        product.setName("Test Product");
        product.setPrice(new Price(99.99,"USD",null));
        productRepository.addNewProduct(product);
        assertNotNull(productRepository.getProductById(13860429));

    }

    @Test
    public void test_addNewProduct_withoutId() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(new Price(99.99,"USD",null));
        if(product.getId() == 0) {
            product.setId(new Random().nextInt(99999999));
        }
        productRepository.addNewProduct(product);
    }

    @Test
    public void test_getProductById() {
        Product product = productRepository.getProductById(13860428);
        assertNotNull(product);
        assertEquals("The Big Lebowski (Blu-ray)",product.getName());
        assertEquals(13860428, product.getId());
    }

    @Test
    public void test_getProductByInValidId() throws ProductException {
        try {
            Product product = productRepository.getProductById(13860430);
        }catch(Exception e) {
            assert e instanceof ProductNotFoundException;
            assertEquals("Product not found for id:13860430", e.getMessage());
        }
    }

    @Test
    public void test_updateProductById() {
        Product product = new Product();
        product.setId(13860428);
        product.setName("The Big Lebowski (Blu-ray)");
        product.setPrice(new Price(1.49,"USD",null));
        productRepository.updateProductById(product,13860428);

        Product updatedProduct = productRepository.getProductById(13860428);
        double updatedPrice = productRepository.getProductById(13860428).getPrice().getPrice();
        assertNotNull(updatedProduct);
        assertEquals(PRODUCT_NAME,updatedProduct.getName());
        assertEquals(CHECK_PRICE, updatedPrice,0);
    }



}
