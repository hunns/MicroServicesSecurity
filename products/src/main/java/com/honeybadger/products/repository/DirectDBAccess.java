package com.honeybadger.products.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DirectDBAccess {
        private final JdbcTemplate jdbcTemplate;

    public DirectDBAccess(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

}
