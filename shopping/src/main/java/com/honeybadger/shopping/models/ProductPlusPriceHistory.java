package com.honeybadger.shopping.models;

import lombok.*;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPlusPriceHistory {
//    String SQL = "Select " +
//            "p.product_id, p.product_name, p.product_status, " +
//            "pc.priceHistory_id, pc.effective_from, pc.is_active, pc.price " +
//            "from product_tab p, priceHistory_tab pc " +
//            "where p.product_id= pc.product_id " +
//            "and pc.is_active ='ACTIVE';

    private Long productId;
    private String productName;
    private Status productStatus;
    private Set<PriceHistory> priceHistories;
}
