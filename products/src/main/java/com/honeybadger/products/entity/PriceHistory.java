package com.honeybadger.products.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pricehistory_tab")
public class PriceHistory {

    @Id
    @SequenceGenerator(
            name = "priceHistseq",
            sequenceName = "pricehist_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pricehist_seq"
    )
    @Column(name = "pricehistory_id")
    private Long priceHistoryId;

    @Column(name = "effective_from", nullable = false)
    private LocalDateTime effectiveFrom;

    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active", length = 15)
    private PriceHistoryState isActive;


    @ManyToOne(fetch = FetchType.LAZY)
    //name attribute will create a column in the table to store the value.
    // We are just naming the same name what the product table is id is having.
    @JoinColumn(name="product_id")
    private Product product;

}
