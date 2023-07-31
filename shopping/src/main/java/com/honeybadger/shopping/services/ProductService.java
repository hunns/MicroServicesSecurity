package com.honeybadger.shopping.services;


import com.honeybadger.shopping.models.Product;
import com.honeybadger.shopping.models.ProductCategory;
import com.honeybadger.shopping.models.ProductPlusProductCategory;
import com.honeybadger.shopping.repositories.ProductCategoryRepository;
import com.honeybadger.shopping.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.java.Log;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    @Transactional
    public ResponseEntity<ProductPlusProductCategory> saveProductPlusProductCategory(ProductPlusProductCategory productProductCategory) {

        Product product = new Product();
        product.setProductName(productProductCategory.getProductName());
        product.setProductStatus(productProductCategory.getProductStatus());
        LOGGER.info("In SaveProductPlusProductCategory Step1: Product Before insertProduct {} ",product );
        Product retProduct = this.repository.insertProduct(product);
        LOGGER.info("In SaveProductPlusProductCategory Step2: Product After insertProduct {} ",retProduct );
        productProductCategory.setProductId(retProduct.getProductId());
        Set<ProductCategory> productCategories = new HashSet<>(productProductCategory.getProductCategories());
        productProductCategory.setProductCategories(this.createProductPlusProductMapping(retProduct, productCategories));
        return new ResponseEntity<>(productProductCategory, HttpStatusCode.valueOf(HttpStatus.SC_OK));
    }

    @Transactional
    public ResponseEntity<ProductPlusProductCategory> updateProductWithProductCategory(ProductPlusProductCategory productPlusProductCategory) {
        try {

            Product foundProduct = this.repository.getProductById(productPlusProductCategory.getProductId()).get();
            foundProduct.setProductName(productPlusProductCategory.getProductName());
            foundProduct.setProductStatus(productPlusProductCategory.getProductStatus());
            if (this.repository.updateProduct(foundProduct)) {
                this.repository.deleteMappingByProductId(foundProduct.getProductId());
            }
            Set<ProductCategory> productCategories = new HashSet<>(productPlusProductCategory.getProductCategories());
            productPlusProductCategory.setProductCategories(this.createProductPlusProductMapping(foundProduct, productCategories));
            return new ResponseEntity<>(productPlusProductCategory, HttpStatusCode.valueOf(HttpStatus.SC_OK));

        } catch (Exception exception) {
            LOGGER.error("Unable to update product {}, Error: {}",productPlusProductCategory,exception.toString());
            return new ResponseEntity<>(productPlusProductCategory, HttpStatusCode.valueOf(HttpStatus.SC_METHOD_FAILURE));
        }

    }


    private Set<ProductCategory> createProductPlusProductMapping(Product retProduct, Set<ProductCategory> productCategories) {
        LOGGER.info("createProductPlusProductMapping is called, number of categories for saving are " + productCategories.size());
        if (productCategories.size() == 0) {
            productCategories.add(ProductCategory.builder()
                    .productCategoryName("_UNKNOWN")
                    .build());
        }

        return productCategories.stream()
                .map(p -> {
                    LOGGER.info("Product category {} is before finding for its existence",p);
                    ProductCategory tmpPc = this.productCategoryRepository
                            .findByProductCategoryName(p.getProductCategoryName());
                    LOGGER.info("Product category {} is after finding for its existence",tmpPc);


                    if (tmpPc == null) {
                        LOGGER.info("Product category {} is about to create",p);
                        p = this.productCategoryRepository.insertProductCategory(p);
                        LOGGER.info("Product category {} is created",p);
                    } else {
                        p = tmpPc;
                        LOGGER.info("Product category {} is already exists",p);
                    }

                    if (!this.repository.insertMapTableForExistingProductCategory(
                            retProduct.getProductId(),
                            p.getProductCategoryId())
                    ) {
                        this.LOGGER.error("Error: Could not create the mapping details for {},{}", retProduct, p);
                    }
                    return p;
                }).collect(Collectors.toSet());
    }

    public List<Product> getAllProductsWithoutProductCategory() {
        return this.repository.getAllProducts();

    }

    public List<ProductPlusProductCategory> getAllProductsWithProductCategory() {
        return this.repository.getAllProductsWithProductCategory();

    }

    public Product updateProduct(Long productId, Product product) {
        try {
            Product foundProduct = this.repository.getProductById(productId).get();
            foundProduct.setProductName(product.getProductName());
            foundProduct.setProductStatus(product.getProductStatus());
            if (this.repository.updateProduct(foundProduct)) {
                return foundProduct;
            } else {
                return null;
            }
        } catch (Exception exception) {
            return null;
        }

    }


    public boolean deleteProductById(Long productId) {
       return this.repository.deleteProductById(productId);
    }

    public boolean deleteProductByName(String productName) {
        return this.repository.deleteProductByName(productName);
    }

    public Product getProductById(Long productId) {
        return this.repository.getProductById(productId).orElseThrow(
                ()-> new NotFoundException(String.format("Product not found",productId))
        );
//        try {
//            Product foundProduct = this.repository.getProductById(productId).get();
//            return new ResponseEntity<>(foundProduct, HttpStatusCode.valueOf(HttpStatus.SC_OK));
//        } catch (Exception exception) {
//            return new ResponseEntity<>(null, HttpStatusCode.valueOf(HttpStatus.SC_METHOD_FAILURE));
//        }
    }
}