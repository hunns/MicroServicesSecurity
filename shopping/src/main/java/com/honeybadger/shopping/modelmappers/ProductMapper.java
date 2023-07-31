package com.honeybadger.shopping.modelmappers;


import com.honeybadger.shopping.models.Product;
import com.honeybadger.shopping.models.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        System.out.println("Product Mapper and Rownum value "+rowNum);
        return Product.builder()
                .productId(rs.getLong("product_id"))
                .productName(rs.getString("product_name"))
                .productStatus(Status.valueOf(rs.getString("product_status")))
                .build();
    }
}
