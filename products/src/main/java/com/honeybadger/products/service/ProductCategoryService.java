package com.honeybadger.products.service;

import com.honeybadger.products.entity.Product;
import com.honeybadger.products.entity.ProductCategory;
import com.honeybadger.products.repository.ProductCategoryRepository;
import com.honeybadger.products.repository.ProductRepository;
import com.honeybadger.products.requestsResposes.ProductCategoryResponse;
import com.honeybadger.products.requestsResposes.ProductResponse;
import com.honeybadger.products.requestsResposes.ResponseStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return this.repository.save(productCategory);
    }

    public List<ProductCategory> getAllProductCategories() {

        return this.repository.findAllByOrderByProductCategoryIdAsc();
    }

    public ProductCategoryResponse updateProductCategory(Long productCategoryId, ProductCategory productCategory) {
        try {
            ProductCategory foundProductCategory = this.repository.findById(productCategoryId).get();
            foundProductCategory.setProductCategoryName(productCategory.getProductCategoryName());
            return ProductCategoryResponse.builder()
                    .responseMessage("Product category updated")
                    .responseStatus(ResponseStatus.RESPONSE_OK)
                    .productCategory(this.repository.save(foundProductCategory))
                    .build();
        } catch (Exception exception) {
            return ProductCategoryResponse.builder()
                    .responseMessage("Invalid Product category to update")
                    .responseStatus(ResponseStatus.RESPONSE_FAILED)
                    .build();
        }

    }

    public void deleteProductCategory(Long productCategoryId) {

        this.repository.deleteById(productCategoryId);
    }

    public ProductCategoryResponse getProductCategory(Long productCategoryId) {
        try {
            ProductCategory foundProductCategory = this.repository.findById(productCategoryId).get();

            return ProductCategoryResponse.builder()
                    .responseMessage("Product category found")
                    .responseStatus(ResponseStatus.RESPONSE_OK)
                    .productCategory(foundProductCategory)
                    .build();
        } catch (Exception exception) {
            return ProductCategoryResponse.builder()
                    .responseMessage("Product category not found")
                    .responseStatus(ResponseStatus.RESPONSE_FAILED)
                    .build();
        }

    }
}