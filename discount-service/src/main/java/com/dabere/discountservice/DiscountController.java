package com.dabere.discountservice;

import com.dabere.discountservice.dto.DiscountDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/discount")
public class DiscountController {

    private final ModelMapper modelMapper;
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
        this.modelMapper = new ModelMapper();
    }


    @GetMapping(path = "/{skuCode}")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable String skuCode) {
        return discountService.getActiveDiscount(skuCode)
                .map(discount -> {
                    DiscountDto discountDto = modelMapper.map(discount, DiscountDto.class);
                    return ResponseEntity.ok(discountDto);
                })
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping("/")
    public ResponseEntity<String> createDiscount(@RequestParam String skuCode, @RequestParam Float discountAmount,
                                     @RequestParam Integer discountPercent, @RequestParam String startDate, @RequestParam String endDate) {
        boolean wasCreated = discountService
                .createDiscount(skuCode, discountAmount, discountPercent, startDate, endDate);
        if (wasCreated) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PutMapping("/activate/{discountId}")
    public ResponseEntity<String> activateDiscount(@PathVariable Long discountId) {
        if (discountService.activateDiscount(discountId)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/deactivate/{discountId}")
    public ResponseEntity<String> deactivateDiscount(@PathVariable long discountId) {
        if (discountService.deactivateDiscount(discountId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    // Here be methods for debugging or other not business logic related methods


    @GetMapping("/")
    public List<DiscountDto> getAllDiscounts() {
        return new ArrayList<>();
    }

}
