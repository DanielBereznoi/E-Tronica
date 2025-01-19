package com.dabere.discountservice;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public boolean createDiscount(String skuCode, Float discountAmount, Integer discountPercent,
                                  String startDate, String endDate) {
        try {
            if (skuCode == null || skuCode.isEmpty()) {
                throw new IllegalArgumentException("SKU code cannot be null or empty.");
            }
            if (discountAmount == null || discountAmount <= 0) {
                throw new IllegalArgumentException("Discount amount must be a positive number.");
            }
            if (discountPercent == null || discountPercent <= 0) {
                throw new IllegalArgumentException("Discount percent must be a positive number.");
            }
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("Start date and end date cannot be null.");
            }
            Timestamp startTimestamp = Timestamp.valueOf(startDate);
            Timestamp endTimestamp = Timestamp.valueOf(endDate);
            if (endTimestamp.before(startTimestamp)) {
                throw new IllegalArgumentException("End date must be after the start date.");
            }

            Discount discount = Discount.builder()
                    .skuCode(skuCode)
                    .discountAmount(discountAmount)
                    .discountPercent(discountPercent)
                    .startDate(startTimestamp)
                    .endDate(endTimestamp)
                    .build();
            discountRepository.save(discount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public Optional<Discount> getDiscount(Long discountId) {
        return discountRepository.findDiscountByDiscountId(discountId);
    }

    public boolean deactivateDiscount(Long discountId) {
        Optional<Discount> discount = discountRepository.findDiscountByDiscountId(discountId);
        discount.ifPresent(d -> {
            d.setIsActive(false);
            discountRepository.save(d);
        });
        return discount.isPresent();
    }


    public Optional<Discount> getActiveDiscount(String skuCode) {
        return discountRepository
                .findDiscountBySkuCodeAndIsActiveOrderByCreatedDesc(skuCode, true)
                .stream()
                .findFirst();
    }

    public boolean activateDiscount(Long discountId) {
        Optional<Discount> discount = discountRepository.findDiscountByDiscountId(discountId);
        discount.ifPresent(d -> {
            Optional<Discount> activeDiscount = getActiveDiscount(d.getSkuCode());
            activeDiscount.ifPresent(ad -> {
                ad.setIsActive(false);
            });
            d.setIsActive(true);
            discountRepository.save(d);
        });
        return discount.isPresent();
    }
}
