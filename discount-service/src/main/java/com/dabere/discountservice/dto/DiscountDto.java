package com.dabere.discountservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private Long discountId;
    private String skuCode;
    private Float discountAmount;
    private Integer discountPercent;
    private Boolean isActive;
    private Timestamp created;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp deactivationTimestamp;

}
