package com.honeybadger.shopping.modelmappers;


import com.honeybadger.shopping.models.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ProductCategoryPlusProductMapper implements RowMapper<ProductCategoryPlusProduct> {

    @Override
    public ProductCategoryPlusProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
    int i=0;
    ProductCategoryPlusProduct productCategoryPlusProduct = null;
    Set<Product> productSet= new HashSet<>();
        while (rs.next()){
            if (i==0) {
                productCategoryPlusProduct=  ProductCategoryPlusProduct.builder()
                        .productCategoryId(rs.getLong("productcategory_id"))
                        .productCategoryName(rs.getString("productcategory_name"))
                        .build();
            }
            productSet.add(
                    Product.builder()
                            .productId(rs.getLong("product_id"))
                            .productName(rs.getString("product_name"))
                            .productStatus(Status.valueOf(rs.getString("product_status")))
                            .build()
            );
        i=i+1;
        }
        if( productCategoryPlusProduct != null) {
            productCategoryPlusProduct.setProducts(productSet);
        }
        return productCategoryPlusProduct;
    }
}
