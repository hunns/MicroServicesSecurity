package com.honeybadger.shopping.DAOs;

import com.honeybadger.shopping.models.PriceHistory;
import com.honeybadger.shopping.models.Product;
import com.honeybadger.shopping.models.ProductPlusPriceHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceHistoryDAO {
    Optional<ProductPlusPriceHistory> getActivePriceHistoryByProduct(Long ProductId);

    Optional<ProductPlusPriceHistory> getPriceHistoryByProduct(Long ProductId);

     Optional<ProductPlusPriceHistory> getPriceHistoriesByProductsAndBetweenDates(Long ProductId, LocalDateTime fromDate, LocalDateTime toDate);

    boolean deletePriceHistoriesForProduct(Long productId);


    boolean updatePriceHistory(PriceHistory priceHistory);

    Product insertPriceHistory(PriceHistory priceHistory);

//    boolean insertMapTableForExistingProductCategory(Long productId, Long productCategoryId);
//
//    List<ProductPlusProductCategory>  getAllProductsWithProductCategory();
//
//    boolean deleteMappingByProductId(Long productId);
}