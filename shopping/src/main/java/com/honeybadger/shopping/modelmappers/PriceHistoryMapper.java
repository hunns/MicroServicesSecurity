package com.honeybadger.shopping.modelmappers;


import com.honeybadger.shopping.models.PriceHistory;
import com.honeybadger.shopping.models.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PriceHistoryMapper implements RowMapper<PriceHistory> {

    @Override
    public PriceHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PriceHistory.builder()
                .priceHistoryId(rs.getLong("pricehistory_id"))
                .isActive(rs.getObject("pricehistory_status", Status.class))
                .effectiveFrom(rs.getObject("effective_from", LocalDateTime.class))
                .build();
    }
}
