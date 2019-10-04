package com.myRetail.demo.restservice.repository;

import com.myRetail.demo.restservice.domain.Product;

public interface IProductRepository {

    Product addNewProduct(Product product);
    Product getProductById(int id);
    Product updateProductById(Product product, int id);
}
