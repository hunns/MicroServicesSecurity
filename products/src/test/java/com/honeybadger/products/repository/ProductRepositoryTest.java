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

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;

    @Test
    public void saveProductWithCategory() {
        Set<ProductCategory> productCategoryList =new HashSet<>();

        productCategoryList.add(
         ProductCategory.builder()
                .productCategoryName("TV")
                .build()
        );

        productCategoryList.add(
                ProductCategory.builder()
                        .productCategoryName("Electronic")
                        .build()

        );

        Product product = Product.builder()
                .productName("Samsung TV")
                .productCategories(productCategoryList)
                .build();

        System.out.println(this.repository.save(product));
    }
}