package com.honeybadger.products.requestsResposes;

import com.honeybadger.products.entity.Product;
import com.honeybadger.products.entity.ProductCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryResponse {
    @Enumerated(EnumType.STRING)
    private ResponseStatus responseStatus;
    private String responseMessage;
    private ProductCategory productCategory;
}
