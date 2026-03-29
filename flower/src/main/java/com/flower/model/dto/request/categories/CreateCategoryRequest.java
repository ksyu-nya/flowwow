package com.flower.model.dto.request.categories;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {

    @NotBlank(message = "Название обязательно")
    private String name;

    @NotBlank(message = "Slug обязателен")
    private String slug;

    private String description;
    private String imageUrl;
}
