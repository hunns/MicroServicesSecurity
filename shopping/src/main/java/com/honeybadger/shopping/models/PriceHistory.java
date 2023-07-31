package com.honeybadger.shopping.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceHistory {
    private Long priceHistoryId;
    private String product_id;
    private Status isActive;
    private LocalDateTime effectiveFrom;
    private double price;
}
