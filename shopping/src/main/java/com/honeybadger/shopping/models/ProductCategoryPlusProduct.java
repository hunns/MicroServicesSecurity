package com.honeybadger.shopping.models;

import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryPlusProduct {
    private Long productCategoryId;
    private String productCategoryName;
    private Set<Product> products;
}
