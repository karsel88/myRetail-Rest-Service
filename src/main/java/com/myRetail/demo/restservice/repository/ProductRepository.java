package com.myRetail.demo.restservice.repository;

import com.myRetail.demo.restservice.domain.Product;
import com.myRetail.demo.restservice.exception.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository implements IProductRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Product addNewProduct(Product product) {
        mongoTemplate.save(product);
        return product;
    }

    @Override
    public Product getProductById(int id) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(id));
            Product product = mongoTemplate.findOne(query, Product.class);
            if(product == null){throw new ProductException("Product not found for id:",String.valueOf(id));}
            return product;
   }

    @Override
    public Product updateProductById(Product product) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(product.getId()));
        if(mongoTemplate.findOne(query,Product.class) != null){
            mongoTemplate.updateFirst(query,Update.update("price",product.getPrice()),Product.class);
            return mongoTemplate.findOne(query,Product.class);
        }else { throw new ProductException("Product not found for id:",String.valueOf(product.getId())); }
    }
}
