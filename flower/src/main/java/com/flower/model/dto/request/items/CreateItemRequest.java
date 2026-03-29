package com.flower.model.dto.request.items;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateItemRequest {

    @NotBlank(message = "Название обязательно")
    private String name;

    private String description;

    @NotNull(message = "Цена обязательна")
    @Positive(message = "Цена должна быть положительной")
    private BigDecimal price;

    private String imgUrl;

    private boolean isHit = false;

    private boolean isNew = false;

    @NotNull(message = "Категория обязательна")
    private Long categoryId;


}
