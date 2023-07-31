package com.honeybadger.products.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductCategoryServiceTest {
    @Autowired
    ProductCategoryService productCategoryService;

    @Test
    public void getProductcategoriesWithProduct() {
        productCategoryService.getAllProductCategories().forEach(System.out::println);
    }

}