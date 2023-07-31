package com.honeybadger.products.repository;

import com.honeybadger.products.entity.Product;
import com.honeybadger.products.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void saveCategoryAlongWithProducts(){
        Set<Product> productList=new HashSet<>();
        productList.add (
                Product.builder()
                        .productName("LG TV")
                        .build()
        );
        productList.add (
                Product.builder()
                        .productName("Redmi TV")
                        .build()
        );

        ProductCategory productCategory= ProductCategory.builder()
                .productCategoryName("Electronics")
                .products(productList)
                .build();
        System.out.println(this.repository.save(productCategory));

    }

    @Test
    public void saveCategoryWithoutProduct(){

        ProductCategory productCategory= ProductCategory.builder()
                .productCategoryName("Home Decor")
                .build();
        System.out.println(this.repository.save(productCategory));

    }





}