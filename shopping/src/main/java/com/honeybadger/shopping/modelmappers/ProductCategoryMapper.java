package com.honeybadger.shopping.modelmappers;


import com.honeybadger.shopping.models.ProductCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCategoryMapper implements RowMapper<ProductCategory> {

    @Override
    public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductCategory.builder()
                .productCategoryId(rs.getLong("productCategory_id"))
                .productCategoryName(rs.getString("productCategory_name"))
                .build();
    }
}
