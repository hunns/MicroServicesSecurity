package com.honeybadger.shopping.repositories;

import com.honeybadger.shopping.DAOs.PriceHistoryDAO;
import com.honeybadger.shopping.models.PriceHistory;
import com.honeybadger.shopping.models.Product;
import com.honeybadger.shopping.models.ProductPlusPriceHistory;
import com.honeybadger.shopping.models.Status;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PriceHistoryRepository implements PriceHistoryDAO {
    JdbcTemplate jdbcTemplate;

    //"create table product_tab (product_id bigint, product_name varchar(255), product_status int)";

    private final Logger LOGGER = LoggerFactory.getLogger(PriceHistoryRepository.class);
    @Autowired
    public PriceHistoryRepository(HikariDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<ProductPlusPriceHistory> getActivePriceHistoryByProduct(Long ProductId) {
        String SQL = "Select " +
                "p.product_id, p.product_name, p.product_status " +
                "pc.priceHistory_id, pc.effective_from, pc.is_active, pc.price " +
                "from product_tab p, priceHistory_tab pc " +
                "where p.product_id= pc.product_id " +
                "and pc.is_active ='ACTIVE'";

                return getProductPriceHistoryUsingQuery(SQL);

    }

    private Optional<ProductPlusPriceHistory> getProductPriceHistoryUsingQuery(String SQL){
        return jdbcTemplate.query(
                SQL, new ResultSetExtractor<Optional<ProductPlusPriceHistory>>() {
                    @Override
                    public Optional<ProductPlusPriceHistory> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        int i = 0;
                        //List<ProductPlusProductCategory> productPlusProductCategoryList = new ArrayList<ProductPlusProductCategory>();
                        ProductPlusPriceHistory productPriceHistory = null;
                        Set<PriceHistory> priceHistorySet = new HashSet<PriceHistory>();
                        //  String prevProductName = "";
                        while (rs.next()) {

                            if (i==0){
                                productPriceHistory = ProductPlusPriceHistory.builder()
                                        .productId(rs.getLong("product_id"))
                                        .productName(rs.getString("product_Name"))
                                        .productStatus(Status.valueOf(rs.getString("product_status")))
                                        .build();
                            }
//pc.priceHistory_id, pc.effective_from, pc.is_active, pc.price
                            priceHistorySet.add(
                                    PriceHistory.builder()
                                            .priceHistoryId(rs.getLong("productHistory_id"))
                                            .effectiveFrom(rs.getObject("effective_from",LocalDateTime.class))
                                            .isActive(Status.valueOf(rs.getString("is_active")))
                                            .price(rs.getDouble("price"))
                                            .build()

                            );

                            i = i + 1;
                        }
                        if (i > 0) {
                            productPriceHistory.setPriceHistories(priceHistorySet);
                        }
                        return Optional.ofNullable(productPriceHistory);
                    }
                }
        );
    }

    @Override
    public Optional<ProductPlusPriceHistory> getPriceHistoryByProduct(Long ProductId) {
        String SQL = "Select " +
                "p.product_id, p.product_name, p.product_status " +
                "pc.priceHistory_id, pc.effective_from, pc.is_active, pc.price " +
                "from product_tab p, priceHistory_tab pc " +
                "where p.product_id= pc.product_id " +
                "order by pc.effective_from";

        return getProductPriceHistoryUsingQuery(SQL);
    }


    @Override
    public Optional<ProductPlusPriceHistory> getPriceHistoriesByProductsAndBetweenDates(Long ProductId, LocalDateTime fromDate, LocalDateTime toDate) {
        String SQL = "Select " +
                "p.product_id, p.product_name, p.product_status " +
                "pc.priceHistory_id, pc.effective_from, pc.is_active, pc.price " +
                "from product_tab p, priceHistory_tab pc " +
                "where p.product_id= pc.product_id " +
                "order by pc.effective_from";

        return getProductPriceHistoryUsingQuery(SQL);
    }

    @Override
    public boolean deletePriceHistoriesForProduct(Long productId) {
        return false;
    }

    @Override
    public boolean updatePriceHistory(PriceHistory priceHistory) {
        return false;
    }

    @Override
    public Product insertPriceHistory(PriceHistory priceHistory) {
        return null;
    }
}
