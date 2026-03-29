package com.flower.model.dto.request.items;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateItemRequest {

    private String name;
    private String description;
    private String composition;

    @Positive(message = "Цена должна быть положительной")
    private BigDecimal price;

    private String imageUrl;
    private Boolean isHit;
    private Boolean isNew;
    private Long categoryId;
}