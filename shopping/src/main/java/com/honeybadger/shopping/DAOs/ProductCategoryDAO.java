package com.honeybadger.shopping.DAOs;


import com.honeybadger.shopping.models.ProductCategory;
import com.honeybadger.shopping.models.ProductCategoryPlusProduct;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryDAO{
    Optional<ProductCategory> getProductCategory(Long Id);
    List<ProductCategory> getAllProductCategories();
    boolean deleteProductCategory(Long productCategoryId);
    boolean deleteProductCategoryByName(String productCategoryName);
    boolean updateProductCategory(ProductCategory productCategory);
    ProductCategory  insertProductCategory(ProductCategory productCategory);
    ProductCategory findByProductCategoryName(String productCategoryName);
    List<ProductCategoryPlusProduct> getAllProductCategoriesWithProduct();
}