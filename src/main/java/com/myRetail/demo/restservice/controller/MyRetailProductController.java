package com.myRetail.demo.restservice.controller;

import com.myRetail.demo.restservice.domain.Product;
import com.myRetail.demo.restservice.service.MyRetailProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyRetailProductController {

    @Autowired
    private MyRetailProductService service;

    @RequestMapping(method = RequestMethod.GET,value = "/products/{id}")
    public Product getProductById(@PathVariable(value="id") int id) {
        return service.getProductById(id);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/products/{id}")
    public Product updateProduct(@PathVariable(value="id") int id, @RequestBody Product product){
        return service.updateProductById(product);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/products/addNew")
    public Product addNewProduct(@RequestBody Product product){
        return service.addNewProduct(product);
    }
}
