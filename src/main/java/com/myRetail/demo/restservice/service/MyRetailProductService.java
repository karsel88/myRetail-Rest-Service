package com.myRetail.demo.restservice.service;

import com.myRetail.demo.restservice.domain.Price;
import com.myRetail.demo.restservice.domain.Product;
import com.myRetail.demo.restservice.domain.Products;
import com.myRetail.demo.restservice.exception.ProductException;
import com.myRetail.demo.restservice.exception.ProductNotFoundException;
import com.myRetail.demo.restservice.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Random;

@Component
@Slf4j
public class MyRetailProductService implements IMyRetailProductService {

    @Value("${api-redsky.host}")
    private String host;

    @Value("${api-redsky.params}")
    private String params;

    @Autowired
    private RestTemplate restTemplate;

    private final IProductRepository productRepository;

    public MyRetailProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /*
     * Method to get product details includes product description (from external source) and price for that product retrieved from database
     */
    @Override
    @Cacheable("product")
    public Product getProductById(int id) {
        try {
            String productDescription = getProductDescriptionById(id);
            Product product = new Product();
            product.setName(productDescription);
            product.setId(id);
            product.setPrice(productRepository.getProductById(id).getPrice());
            Thread.sleep(1000 * 5); // Added just to demonstrate the caching
            return product;
        }catch(ProductNotFoundException e ){
            throw new ProductNotFoundException("Product not found for id:",String.valueOf(id));
        }catch(InterruptedException e){
            log.info("Thread interrupted during sleep ", e.getMessage());
            return null;
        }
    }
    /*
     * Method to update the product price for a particular product id
     */
    @Override
    @CacheEvict(cacheNames="product", key ="#root.args[0].id")
    public Product updateProductById(Product product, int id) {
            return validateProduct(product)? productRepository.updateProductById(product,id) : null;
    }

    /*
     * This Method adds a new product to our product database
     */
    @Override
    public Product addNewProduct(Product product) {
        if(product.getId() == 0) {
            product.setId(new Random().nextInt(99999999));
        }
        return validateProduct(product)? productRepository.addNewProduct(product) : null ;
    }

    /*
    * Method to call an external API to get product description for each of product ID
    */
    public String getProductDescriptionById(int productId){
            String productDescription = null;
            try {

                String url = MessageFormat.format("{0}/tcin/{1}?{2}", host, String.valueOf(productId), params);

                HttpEntity<Object> entity = new HttpEntity<>(new HttpHeaders());

                ResponseEntity<Products> result = restTemplate.exchange(url, HttpMethod.GET, entity, Products.class);

                Products products = result.getBody();
                assert products != null;
                if (products.getProduct().getItem().getProductDescription() != null) {
                    productDescription = products.getProduct().getItem().getProductDescription().getTitle();
                }
            } catch (HttpClientErrorException e) {
                log.warn("Errored out while fetching product from Redsky " + e.getResponseBodyAsString());
            }
            return productDescription;
        }

    private boolean validateProduct(Product product){
        Price price = product.getPrice();
        if (price.getPrice() == null || price.getPrice() <= 0.0 ){
            throw new ProductException("Invalid Price:",price.getPrice().toString());
        }
        if(price.getCurrencyCode() == null || price.getCurrencyCode().trim().equals("")){
            throw new ProductException("Currency code cannot be null:",price.getCurrencyCode());
        }
        return true;
    }
}

