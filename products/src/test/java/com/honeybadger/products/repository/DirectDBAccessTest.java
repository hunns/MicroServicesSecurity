package com.honeybadger.products.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DirectDBAccessTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getAllProductsWithCategory(){
        String qry = "select p.*, pc.* " +
                "from product_tab  p, productcategory_tab pc, prd2prdcat_map pm " +
                "where p.product_id = pm.prd_id and pc.productcategory_id= pm.prdcat_id";
        jdbcTemplate.query(qry, RowMapper<?>);
    }


}