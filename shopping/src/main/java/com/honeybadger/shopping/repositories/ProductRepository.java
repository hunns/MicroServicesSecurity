package com.honeybadger.shopping.repositories;


import com.honeybadger.shopping.DAOs.ProductDAO;
import com.honeybadger.shopping.modelmappers.ProductMapper;
import com.honeybadger.shopping.models.Product;
import com.honeybadger.shopping.models.ProductCategory;
import com.honeybadger.shopping.models.ProductPlusProductCategory;
import com.honeybadger.shopping.models.Status;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class ProductRepository implements ProductDAO {
    JdbcTemplate jdbcTemplate;

    //"create table product_tab (product_id bigint, product_name varchar(255), product_status int)";


    @Autowired
    public ProductRepository(HikariDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        String SQL_FIND_PRODUCT = "select * from product_tab " +
                "where product_id = ?";
        //return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_PRODUCT, new ProductMapper()));
        return jdbcTemplate.query(SQL_FIND_PRODUCT,new ProductMapper(), id).
                stream().findFirst();
    }

    @Override
    public Optional<Product> getProductByName(String productName) {
        String SQL_FIND_PRODUCT = "select * from product_tab " +
                "where product_name = ?";
        //return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_PRODUCT, new Object[]{productName}, new ProductMapper()));
        return jdbcTemplate.query(SQL_FIND_PRODUCT,new ProductMapper(), productName).
                stream().findFirst();
    }


    @Override
    public List<Product> getAllProducts() {
        String SQL_FIND_ALL_PRODUCT = "select * from product_tab";
        return jdbcTemplate.query(
                SQL_FIND_ALL_PRODUCT, new ProductMapper()
        );
    }

    @Override
    public boolean deleteProductById(Long productId) {
        String SQL_DELETE_PRODUCT = "delete from product_tab " +
                "where product_id=?";
        return jdbcTemplate.update(
                SQL_DELETE_PRODUCT,
                productId
        ) > 0;
    }

    @Override
    public boolean deleteProductByName(String productName) {
        String SQL_DELETE_PRODUCT = "delete from product_tab " +
                "where product_name=?";
        return jdbcTemplate.update(
                SQL_DELETE_PRODUCT,
                productName
        ) > 0;
    }


    @Override
    public boolean updateProduct(Product product) {
        String SQL_UPDATE_PRODUCT = "update product_tab " +
                "set product_name=? , product_status=? " +
                "where product_id=?";
        return jdbcTemplate.update(
                SQL_UPDATE_PRODUCT,
                product.getProductName(),
                product.getProductStatus().name(),
                product.getProductId()
        ) > 0;
    }

    @Override
    public Product insertProduct(Product product) {
        String SQL_INSERT_PRODUCT = "insert into product_tab" +
                "(product_id, product_name, product_status) " +
                "values(?,?,?)";
        int retVal = jdbcTemplate.update(SQL_INSERT_PRODUCT,
                product.getProductId(),
                product.getProductName(),
                product.getProductStatus().name());

        return getProductByName(product.getProductName()).get();
    }

    @Override
    public boolean insertMapTableForExistingProductCategory(Long productId, Long productCategoryId) {
        String SQL_INSERT_PRODUCT = "insert into prd2prdcat_tab" +
                "(product_id, productcategory_id) " +
                "values(?,?)";
        return jdbcTemplate.update(SQL_INSERT_PRODUCT,
                productId,
                productCategoryId) > 0;

    }

    @Override
    public List<ProductPlusProductCategory> getAllProductsWithProductCategory() {
        String SQL = "Select " +
                "p.product_id, p.product_name, p.product_status, " +
                "pc.productcategory_id, pc.productcategory_name " +
                "from product_tab p, productcategory_tab pc, prd2prdcat_tab p2pc " +
                "where p.product_id= p2pc.product_id " +
                "and pc.productcategory_id = p2pc.productcategory_id order by p2pc.product_id, p2pc.productcategory_id";

        return jdbcTemplate.query(
                SQL, new ResultSetExtractor<List<ProductPlusProductCategory>>() {
                    @Override
                    public List<ProductPlusProductCategory> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        int i = 0;
                        List<ProductPlusProductCategory> productPlusProductCategoryList = new ArrayList<ProductPlusProductCategory>();
                        ProductPlusProductCategory productPlusProductCategory = null;
                        Set<ProductCategory> productCategorySet = new HashSet<>();
                        String prevProductName = "";
                        while (rs.next()) {
                            System.out.println("Row count " + i);

                            if (!prevProductName.equals(rs.getString("product_name"))) {

                                if (i > 0) {
                                    productPlusProductCategory.setProductCategories(productCategorySet);
                                    productPlusProductCategoryList.add(productPlusProductCategory);
                                }

                                productPlusProductCategory = ProductPlusProductCategory.builder()
                                        .productId(rs.getLong("product_id"))
                                        .productName(rs.getString("product_name"))
                                        .productStatus(Status.valueOf(rs.getString("product_status")))
                                        .build();
                                if (productCategorySet != null){
                                    productCategorySet=new HashSet<>();
                                }
                            }

                            productCategorySet.add(
                                    ProductCategory.builder()
                                            .productCategoryId(rs.getLong("productcategory_id"))
                                            .productCategoryName(rs.getString("productcategory_name"))
                                            .build()

                            );
                            prevProductName = rs.getString("product_name");
                            i = i + 1;
                        }
                        if (i > 0) {
                            productPlusProductCategory.setProductCategories(productCategorySet);
                            productPlusProductCategoryList.add(productPlusProductCategory);
                        }
                        return productPlusProductCategoryList;
                    }
                }
        );
    }

    @Override
    public boolean deleteMappingByProductId(Long productId) {
        String SQL = "delete from prd2prdcat_tab " +
                "where product_id=?";
        return jdbcTemplate.update(
                SQL,
                productId
        ) > 0;
    }
}
