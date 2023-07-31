package com.honeybadger.products.service;

import com.honeybadger.products.entity.Product;
import com.honeybadger.products.entity.ProductCategory;
import com.honeybadger.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

@Autowired
    private   ProductService service;
    @Test
    void getAllProducts() {
        List<Product> productList =service.getAllProducts();
        productList.forEach(System.out::println);
    }

    @Test
    void saveProductWithProductCategory() {
        Set<ProductCategory> productCategories= new HashSet<>();
        productCategories.add (ProductCategory.builder()
                .productCategoryName("Electronics")
                .build());

        productCategories.add (ProductCategory.builder()
                .productCategoryName("Digital Storage")
                .build());

        this.service.saveProduct(Product.builder()
                        .productName("SanDisk 64GB Memory")
                        .productCategories(productCategories)
                .build());
    }

}