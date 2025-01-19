package com.dabere.discountservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Optional<Discount> findDiscountByDiscountId(Long id);

    List<Discount> findDiscountBySkuCodeAndIsActiveOrderByCreatedDesc(String skuCode, boolean isActive);

}
