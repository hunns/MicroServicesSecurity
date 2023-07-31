package com.honeybadger.products.repository;

import com.honeybadger.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    public List<Product> findAllByOrderByProductIdAsc();



    @Modifying
    @Query(
            value = "insert into prd2prdcat_map(prd_id, prdcat_id)  values (:prdId, :prdCatid)",
            nativeQuery = true)
    void insertMapTableForExistingProductCategory(@Param("prdId") Long prdId, @Param("prdCatid") Long prdCatId);

}
