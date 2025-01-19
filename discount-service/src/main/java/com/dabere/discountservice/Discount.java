package com.dabere.discountservice;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product_discount")
public class Discount {

    @Id
    @Column(name = "discount_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    @Column(name = "sku_code", nullable = false, length = 12)
    private String skuCode;

    @Column(name = "discount_amount", nullable = false)
    private Float discountAmount;

    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "created", nullable = false)
    private Timestamp created = new Timestamp(System.currentTimeMillis());

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;
    
    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "deactivation_timestamp")
    private Timestamp deactivationTimestamp;


    public String toString() {
        return String.format(
                "Discount id=%s, Sku code=%s, Discount amount=%s, Discount percent=%s, Is active=%s, Created=%s",
                discountId, skuCode, discountAmount, discountPercent, isActive, created);
    }


}
