package com.honeybadger.shopping.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory {
    private Long productCategoryId;
    private String productCategoryName;

}
