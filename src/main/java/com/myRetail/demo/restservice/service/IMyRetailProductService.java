package com.myRetail.demo.restservice.service;

import com.myRetail.demo.restservice.domain.Product;

public interface IMyRetailProductService {
    Product getProductById(int id) throws InterruptedException;

    Product updateProductById(Product product);

    Product addNewProduct(Product product);
}
