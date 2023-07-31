package com.honeybadger.shopping.DAOs;


import com.honeybadger.shopping.models.Product;
import com.honeybadger.shopping.models.ProductPlusProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Optional<Product> getProductById(Long Id);

    Optional<Product> getProductByName(String productName);

    List<Product> getAllProducts();

    boolean deleteProductById(Long productId);

    boolean deleteProductByName(String productName);

    boolean updateProduct(Product product);

    Product insertProduct(Product product);

    boolean insertMapTableForExistingProductCategory(Long productId, Long productCategoryId);

    List<ProductPlusProductCategory>  getAllProductsWithProductCategory();

    boolean deleteMappingByProductId(Long productId);
}