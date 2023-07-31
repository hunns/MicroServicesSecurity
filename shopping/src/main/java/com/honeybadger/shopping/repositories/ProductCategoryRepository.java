package com.honeybadger.shopping.repositories;


import com.honeybadger.shopping.DAOs.ProductCategoryDAO;
import com.honeybadger.shopping.modelmappers.ProductCategoryMapper;
import com.honeybadger.shopping.modelmappers.ProductCategoryPlusProductMapper;
import com.honeybadger.shopping.models.ProductCategory;
import com.honeybadger.shopping.models.ProductCategoryPlusProduct;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductCategoryRepository implements ProductCategoryDAO {
    JdbcTemplate jdbcTemplate;

    //"create table product_tab (product_id bigint, product_name varchar(255), product_status int)";

    private final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryRepository.class);
    @Autowired
    public ProductCategoryRepository(HikariDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Optional<ProductCategory> getProductCategory(Long Id) {
        String SQL_FIND_PRODUCT = "select * from productcategory_tab " +
                "where productcategory_id=" + Id;
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_PRODUCT, new ProductCategoryMapper()));
    }

    @Override
    public List<ProductCategory> getAllProductCategories() {
        String SQL_FIND_ALL_PRODUCT = "select * from productcategory_tab";
        return jdbcTemplate.query(
                SQL_FIND_ALL_PRODUCT, new ProductCategoryMapper()
        );
    }

    @Override
    public boolean deleteProductCategory(Long productCategoryId) {
        String SQL_DELETE_PRODUCT = "delete from productcategory_tab " +
                "where productcategory_id=?";
        return jdbcTemplate.update(
                SQL_DELETE_PRODUCT,
                productCategoryId
        ) > 0;

    }

    @Override
    public boolean deleteProductCategoryByName(String productCategoryName) {
        String SQL_DELETE_PRODUCT = "delete from productcategory_tab " +
                "where productcategory_name=?";
        return jdbcTemplate.update(
                SQL_DELETE_PRODUCT,
                productCategoryName
        ) > 0;
    }

    @Override
    public boolean updateProductCategory(ProductCategory productCategory) {
        String SQL_UPDATE_PRODUCT = "update productcategory_tab " +
                "set productcatogry_name=? " +
                "where productcategory_id=?";
        return jdbcTemplate.update(
                SQL_UPDATE_PRODUCT,
                productCategory.getProductCategoryName(),
                productCategory.getProductCategoryId()
        ) > 0;
    }
    @Override
    public ProductCategory insertProductCategory(ProductCategory productCategory) {
        String SQL_INSERT_PRODUCT = "insert into productcategory_tab" +
                "(productcategory_name) " +
                "values(?)";
         int retval =jdbcTemplate.update(SQL_INSERT_PRODUCT,
                productCategory.getProductCategoryName()
                ) ;
         return findByProductCategoryName(productCategory.getProductCategoryName());
    }

    @Override
    public ProductCategory findByProductCategoryName(String productCategoryName) {
        LOGGER.info("findByProductCategoryName called for {}",productCategoryName);
        String sql = "select * from productcategory_tab " +
        " where productcategory_name =?";

        try {
            return jdbcTemplate.queryForObject(
                    sql, new Object[]{productCategoryName} , new ProductCategoryMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ProductCategoryPlusProduct> getAllProductCategoriesWithProduct() {
        String SQL ="Select " +
                "p.product_id, p.product_name, p.product_status, " +
                "pc.productcategory_id, pc.productcategory_name " +
                "from product_tab p, productcategory_tab pc, prd2prdcat_tab p2pc " +
                "where p.product_id= p2pc.product_id " +
                "and pc.productcategory_id = p2pc.productcategory_id" ;

        return jdbcTemplate.query(
                SQL, new ProductCategoryPlusProductMapper());
    }
}
