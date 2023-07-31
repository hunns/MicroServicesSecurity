package com.honeybadger.shopping.services;

import com.honeybadger.shopping.models.ProductCategory;
import com.honeybadger.shopping.models.ProductCategoryPlusProduct;
import com.honeybadger.shopping.repositories.ProductCategoryRepository;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return this.repository.insertProductCategory(productCategory);
    }

    public List<ProductCategory> getAllProductCategories() {

        return this.repository.getAllProductCategories();
    }

    public List<ProductCategoryPlusProduct> getAllProductCategoriesWithProduct() {
        return this.repository.getAllProductCategoriesWithProduct();
    }

    public ResponseEntity<ProductCategory> updateProductCategory(Long productCategoryId, ProductCategory productCategory) {
        try {
            ProductCategory foundProductCategory = this.repository.getProductCategory(productCategoryId).get();
            foundProductCategory.setProductCategoryName(productCategory.getProductCategoryName());
            return new ResponseEntity<>(foundProductCategory, HttpStatusCode.valueOf(HttpStatus.SC_OK));
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(HttpStatus.SC_METHOD_FAILURE));
        }

    }

    public boolean deleteProductCategoryById(Long productId) {
        return this.repository.deleteProductCategory(productId);
    }

    public boolean deleteProductCategoryByName(String productCategoryName) {
        return this.repository.deleteProductCategoryByName(productCategoryName);
    }

    public ResponseEntity<ProductCategory> getProductCategory(Long productCategoryId) {
        try {
            ProductCategory foundProductCategory = this.repository.getProductCategory(productCategoryId).get();

            return new ResponseEntity<>(foundProductCategory, HttpStatusCode.valueOf(HttpStatus.SC_OK));

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(HttpStatus.SC_METHOD_FAILURE));
        }

    }
}