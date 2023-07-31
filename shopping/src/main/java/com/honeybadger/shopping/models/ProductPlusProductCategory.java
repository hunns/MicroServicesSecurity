package com.honeybadger.shopping.models;

import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPlusProductCategory {
    private Long productId;
    private String productName;
    private Status productStatus;
    private Set<ProductCategory> productCategories;
}
