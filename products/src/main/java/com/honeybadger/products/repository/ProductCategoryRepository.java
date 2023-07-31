package com.honeybadger.products.repository;

import com.honeybadger.products.entity.Product;
import com.honeybadger.products.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {

    public List<ProductCategory> findAllByOrderByProductCategoryIdAsc();

    Optional<ProductCategory> findByProductCategoryName(String s);

  }
