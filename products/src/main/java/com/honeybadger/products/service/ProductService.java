package com.honeybadger.products.service;

import com.honeybadger.products.entity.Product;
import com.honeybadger.products.entity.ProductCategory;
import com.honeybadger.products.repository.ProductCategoryRepository;
import com.honeybadger.products.repository.ProductRepository;
import com.honeybadger.products.requestsResposes.ProductResponse;
import com.honeybadger.products.requestsResposes.ResponseStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Transactional
    public Product saveProduct(Product product) {
        Set<ProductCategory> productCategories = new HashSet<>(product.getProductCategories());
        Set<ProductCategory> newProductCategories = productCategories.stream()
                .filter(p -> {
                    Optional<ProductCategory> tmpPc = this.productCategoryRepository
                            .findByProductCategoryName(p.getProductCategoryName());
                    return tmpPc.isEmpty();
                }).collect(Collectors.toSet());
        product.setProductCategories(newProductCategories);

        productCategories.removeAll(newProductCategories);
        Product retProduct = this.repository.save(product);


        for (ProductCategory pc : productCategories) {
            Optional<ProductCategory> tmpPC = this.productCategoryRepository.findByProductCategoryName(pc.getProductCategoryName());

            if (tmpPC.isPresent()) {
                this.repository.insertMapTableForExistingProductCategory(
                        retProduct.getProductId(), pc.getProductCategoryId()
                );
            }


        }
        return retProduct;
    }


    public List<Product> getAllProducts() {
        return this.repository.findAllByOrderByProductIdAsc();

    }

    public ProductResponse updateProduct(Long productId, Product product) {
        try {
            Product foundProduct = this.repository.findById(productId).get();
            foundProduct.setProductName(product.getProductName());
            return ProductResponse.builder()
                    .responseMessage("Product updated")
                    .responseStatus(ResponseStatus.RESPONSE_OK)
                    .product(this.repository.save(foundProduct))
                    .build();
        } catch (Exception exception) {
            return ProductResponse.builder()
                    .responseMessage("Invalid Product to update")
                    .responseStatus(ResponseStatus.RESPONSE_FAILED)
                    .build();
        }

    }


    public void deleteProduct(Long productId) {
        this.repository.deleteById(productId);
    }

    public ProductResponse getProduct(Long productId) {
        try {
            Product foundProduct = this.repository.findById(productId).get();

            return ProductResponse.builder()
                    .responseMessage("Product found")
                    .responseStatus(ResponseStatus.RESPONSE_OK)
                    .product(foundProduct)
                    .build();
        } catch (Exception exception) {
            return ProductResponse.builder()
                    .responseMessage("Product not found")
                    .responseStatus(ResponseStatus.RESPONSE_FAILED)
                    .build();
        }

    }
}