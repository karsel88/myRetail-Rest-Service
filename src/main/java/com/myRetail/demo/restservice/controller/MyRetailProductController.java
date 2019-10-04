package com.myRetail.demo.restservice.controller;

import com.myRetail.demo.restservice.domain.Product;
import com.myRetail.demo.restservice.service.MyRetailProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
public class MyRetailProductController {

    @Autowired
    private MyRetailProductService service;

    @RequestMapping(method = RequestMethod.GET,value = "/product/{id}")
    public Product getProductById(@PathVariable(value="id") @Min(1) int id) {
        return service.getProductById(id);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/product/{id}")
    public Product updateProduct(@PathVariable(value="id") @Min(1) int id, @Valid @RequestBody Product product){
        return service.updateProductById(product, id);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/product/addNew")
    public Product addNewProduct(@Valid @RequestBody Product product){
        return service.addNewProduct(product);
    }
}
