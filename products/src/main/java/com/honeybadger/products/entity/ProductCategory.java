package com.honeybadger.products.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

@Table(name="productcategory_tab")
public class ProductCategory {
    @Id
    @SequenceGenerator(
            name = "productCategorySeq",
            sequenceName = "productcategory_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "productcategory_seq"
    )
    @Column(name = "productcategory_id")
    private Long productCategoryId;

    @Column(name = "productcategory_Name", unique = true, nullable = false)
    private String productCategoryName;

//    @JsonBackReference
    @ManyToMany(mappedBy = "productCategories", fetch = FetchType.LAZY)
    private Set<Product> products;
}
