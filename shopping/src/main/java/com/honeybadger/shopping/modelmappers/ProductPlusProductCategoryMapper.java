package com.honeybadger.shopping.modelmappers;


import com.honeybadger.shopping.models.Product;
import com.honeybadger.shopping.models.ProductCategory;
import com.honeybadger.shopping.models.ProductPlusProductCategory;
import com.honeybadger.shopping.models.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProductPlusProductCategoryMapper implements RowMapper<ProductPlusProductCategory> {

    @Override
    public ProductPlusProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        int i = 0;
        ProductPlusProductCategory productPlusProductCategory = null;
        Set<ProductCategory> productCategorySet = new HashSet<>();
        System.out.println("First category " + rs.getString("productcategory_name"));
        String prevProductName ="";
        do {
            System.out.println("Row count " + i);

            if (!prevProductName.equals(rs.getString("product_name"))) {
                productPlusProductCategory = ProductPlusProductCategory.builder()
                        .productId(rs.getLong("product_id"))
                        .productName(rs.getString("product_name"))
                        .productStatus(Status.valueOf(rs.getString("product_status")))
                        .build();
                productCategorySet.clear();
            }

            productCategorySet.add(
                    ProductCategory.builder()
                            .productCategoryId(rs.getLong("productcategory_id"))
                            .productCategoryName(rs.getString("productcategory_name"))
                            .build()

            );
            prevProductName = rs.getString("product_name");
            i = i + 1;

        } while (rs.next());
        if (productPlusProductCategory != null) {
            productPlusProductCategory.setProductCategories(productCategorySet);
        }
        return productPlusProductCategory;
    }
}
