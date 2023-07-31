package com.honeybadger.products.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_tab")
public class Product {
    @Id
    @SequenceGenerator(
            name = "Productseq",
            sequenceName = "product_seqe",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_seq"
    )
    @Column(name="product_id")
    private Long productId;

    @Column(name="product_name",unique = true,nullable = false)
    private String productName;

   // @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="prd2prdcat_map",
            joinColumns = @JoinColumn(
                    name = "prd_id",
                    referencedColumnName = "product_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "prdcat_id",
                    referencedColumnName = "productcategory_id"
            )
    )
    private Set<ProductCategory> productCategories;

  }
